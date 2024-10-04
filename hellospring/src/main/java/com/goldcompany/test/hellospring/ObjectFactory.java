package com.goldcompany.test.hellospring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectFactory {
    @Bean
    public PaymentService paymentService() {
        return new PaymentService(cachedExchangeRateProvider());
    }

    @Bean
    public ExchangeRateProvider cachedExchangeRateProvider() {
        return new CachedExchangeRateProvider(exchangeRateProvider());
    }

    @Bean
    public ExchangeRateProvider exchangeRateProvider() {
        return new WebApiExchangeRateProvider();
    }
}
