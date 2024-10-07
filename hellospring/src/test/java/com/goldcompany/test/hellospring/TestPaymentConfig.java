package com.goldcompany.test.hellospring;

import com.goldcompany.test.hellospring.payment.ExchangeRateProvider;
import com.goldcompany.test.hellospring.payment.ExchangeRateProviderStub;
import com.goldcompany.test.hellospring.payment.PaymentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@Configuration
public class TestPaymentConfig {
    @Bean
    public PaymentService paymentService() {
        return new PaymentService(exchangeRateProvider(), clock());
    }

    @Bean
    public ExchangeRateProvider exchangeRateProvider() {
        return new ExchangeRateProviderStub(BigDecimal.valueOf(1_000));
    }

    @Bean
    public Clock clock() {
        return Clock.fixed(Instant.now(), ZoneId.systemDefault());
    }
}
