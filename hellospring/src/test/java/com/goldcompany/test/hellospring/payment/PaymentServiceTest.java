package com.goldcompany.test.hellospring.payment;

import com.goldcompany.test.hellospring.exchangerate.WebApiExchangeRateProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceTest {

    @Test
    void prepare() throws IOException {
        getPayment(BigDecimal.valueOf(1314), BigDecimal.valueOf(1_3140));
        getPayment(BigDecimal.valueOf(1340), BigDecimal.valueOf(1_3400));
        getPayment(BigDecimal.valueOf(1321), BigDecimal.valueOf(1_3210));

        // 원화환산금액 유효시간 계산
//        Assertions.assertThat(payment.getValidUntil()).isAfter(LocalDateTime.now());
//        Assertions.assertThat(payment.getValidUntil()).isBefore(LocalDateTime.now().plusMinutes(30));
    }

    private static void getPayment(BigDecimal exchangeRate, BigDecimal convertedAmount) throws IOException {
        PaymentService paymentService = new PaymentService(new ExchangeRateProviderStub(exchangeRate));

        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        Assertions.assertThat(payment.getExchangedRate()).isEqualByComparingTo(exchangeRate);
        Assertions.assertThat(payment.getConvertedAmount()).isEqualByComparingTo(convertedAmount);
    }
}