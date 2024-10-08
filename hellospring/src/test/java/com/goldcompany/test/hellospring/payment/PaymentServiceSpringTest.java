package com.goldcompany.test.hellospring.payment;

import com.goldcompany.test.hellospring.TestPaymentConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestPaymentConfig.class)
class PaymentServiceSpringTest {
    @Autowired PaymentService paymentService;
    @Autowired ExchangeRateProviderStub exchangeRateProviderStub;
    @Autowired Clock clock;

    @Test
    void convertedAmount() {
        // exchange rate = 1000
        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        Assertions.assertThat(payment.getExchangedRate()).isEqualByComparingTo(BigDecimal.valueOf(1_000));
        Assertions.assertThat(payment.getConvertedAmount()).isEqualByComparingTo(BigDecimal.valueOf(10_000));

        // exchange rate = 1321
        exchangeRateProviderStub.setExchangeRate(BigDecimal.valueOf(1321));
        Payment payment2 = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        Assertions.assertThat(payment2.getExchangedRate()).isEqualByComparingTo(BigDecimal.valueOf(1_321));
        Assertions.assertThat(payment2.getConvertedAmount()).isEqualByComparingTo(BigDecimal.valueOf(13_210));
    }

    @Test
    void validUntil() {
        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        LocalDateTime now = LocalDateTime.now(this.clock);
        LocalDateTime expectedValidUntil = now.plusMinutes(30);

        Assertions.assertThat(payment.getValidUntil()).isEqualTo(expectedValidUntil);
    }

}