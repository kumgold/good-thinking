package com.goldcompany.test.hellospring;

import com.goldcompany.test.hellospring.payment.ExchangeRateProvider;
import com.goldcompany.test.hellospring.exchangerate.WebApiExchangeRateProvider;
import com.goldcompany.test.hellospring.payment.PaymentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class PaymentConfig {
    @Bean
    public PaymentService paymentService() {
        return new PaymentService(exchangeRateProvider(), clock());
    }

    @Bean
    public ExchangeRateProvider exchangeRateProvider() {
        return new WebApiExchangeRateProvider();
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
