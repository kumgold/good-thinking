package com.goldcompany.test.hellospring.exchangerate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goldcompany.test.hellospring.api.ApiExecutor;
import com.goldcompany.test.hellospring.api.ErApiExchangeRateExtractor;
import com.goldcompany.test.hellospring.api.ExchangeRateExtractor;
import com.goldcompany.test.hellospring.api.SImpleApiExecutor;
import com.goldcompany.test.hellospring.payment.ExchangeRateProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

public class WebApiExchangeRateProvider implements ExchangeRateProvider {
    @Override
    public BigDecimal getExchangeRate(String currency) {
        String url = "https://open.er-api.com/v6/latest/" + currency;
        return runApiForExchangeRate(url, new SImpleApiExecutor(), new ErApiExchangeRateExtractor());
    }

    private static BigDecimal runApiForExchangeRate(String url, ApiExecutor apiExecutor, ExchangeRateExtractor exchangeRateExtractor) {
        URI uri;

        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        String response;
        try {
            response = apiExecutor.executeApi(uri);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            return exchangeRateExtractor.extract(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
