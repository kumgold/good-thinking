package com.goldcompany.test.hellospring.exchangerate;

import com.goldcompany.test.hellospring.payment.ExchangeRateProvider;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class RestTemplateExchangeRateProvider implements ExchangeRateProvider {
    private final RestTemplate restTemplate;

    public RestTemplateExchangeRateProvider(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public BigDecimal getExchangeRate(String currency) {
        String url = "https://open.er-api.com/v6/latest/" + currency;

        return restTemplate.getForObject(url, ExchangeReteData.class).rates().get("KRW");
    }
}
