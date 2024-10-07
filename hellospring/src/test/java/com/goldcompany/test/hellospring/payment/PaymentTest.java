package com.goldcompany.test.hellospring.payment;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.*;
import java.time.temporal.ChronoUnit;

public class PaymentTest {
    Clock clock;
    Payment payment;

    @BeforeEach
    void beforeEach() {
        clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

        payment = Payment.create(
                1L,
                "USD",
                BigDecimal.TEN,
                BigDecimal.valueOf(1_000),
                LocalDateTime.now(clock)
        );
    }

    @Test
    void createPayment() {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

        Payment payment = Payment.create(
                1L,
                "USD",
                BigDecimal.TEN,
                BigDecimal.valueOf(1_000),
                LocalDateTime.now(clock)
        );

        Assertions.assertThat(payment.getConvertedAmount()).isEqualByComparingTo(BigDecimal.valueOf(10_000));
        Assertions.assertThat(payment.getValidUntil()).isEqualTo(LocalDateTime.now(clock).plusMinutes(30));
    }

    @Test
    void isValidUntil() {
        Assertions.assertThat(payment.isValid(clock)).isTrue();
        Assertions.assertThat(
                payment.isValid(Clock.offset(clock, Duration.of(30, ChronoUnit.MINUTES)))
        ).isFalse();
    }
}
