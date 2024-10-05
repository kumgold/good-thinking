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
        PaymentService paymentService = new PaymentService(new WebApiExchangeRateProvider());

       Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        // 환율정보
        Assertions.assertThat(payment.getExchangedRate()).isNotNull();

        // 원화환산금액 계산
        Assertions.assertThat(payment.getConvertedAmount())
                .isEqualTo(payment.getExchangedRate().multiply(payment.getForeignCurrencyAmount()));

        // 원화환산금액 유효시간 계산
        Assertions.assertThat(payment.getValidUntil()).isAfter(LocalDateTime.now());
        Assertions.assertThat(payment.getValidUntil()).isBefore(LocalDateTime.now().plusMinutes(30));
    }
}