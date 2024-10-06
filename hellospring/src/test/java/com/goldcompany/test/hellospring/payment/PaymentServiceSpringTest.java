package com.goldcompany.test.hellospring.payment;

import com.goldcompany.test.hellospring.TestObjectFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestObjectFactory.class)
class PaymentServiceSpringTest {
    @Autowired PaymentService paymentService;
    @Autowired ExchangeRateProviderStub exchangeRateProviderStub;

    @Test
    void prepare() throws IOException {
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
}