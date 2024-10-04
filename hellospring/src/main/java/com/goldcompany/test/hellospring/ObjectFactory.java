package com.goldcompany.test.hellospring;

import com.goldcompany.test.hellospring.exchangerate.CachedExchangeRateProvider;
import com.goldcompany.test.hellospring.payment.ExchangeRateProvider;
import com.goldcompany.test.hellospring.exchangerate.WebApiExchangeRateProvider;
import com.goldcompany.test.hellospring.payment.PaymentService;
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
