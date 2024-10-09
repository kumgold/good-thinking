package com.goldcompany.test.hellospring;

import com.goldcompany.test.hellospring.exchangerate.RestTemplateExchangeRateProvider;
import com.goldcompany.test.hellospring.payment.ExchangeRateProvider;
import com.goldcompany.test.hellospring.payment.PaymentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Clock;

@Configuration
public class PaymentConfig {
    @Bean
    public PaymentService paymentService() {
        return new PaymentService(exchangeRateProvider(), clock());
    }

    @Bean
    public ExchangeRateProvider exchangeRateProvider() {
        return new RestTemplateExchangeRateProvider(restTemplate());
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
