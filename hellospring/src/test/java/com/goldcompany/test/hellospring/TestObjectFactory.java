package com.goldcompany.test.hellospring;

import com.goldcompany.test.hellospring.payment.ExchangeRateProvider;
import com.goldcompany.test.hellospring.payment.ExchangeRateProviderStub;
import com.goldcompany.test.hellospring.payment.PaymentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class TestObjectFactory {
    @Bean
    public PaymentService paymentService() {
        return new PaymentService(exchangeRateProvider());
    }

    @Bean
    public ExchangeRateProvider exchangeRateProvider() {
        return new ExchangeRateProviderStub(BigDecimal.valueOf(1_000));
    }
}
