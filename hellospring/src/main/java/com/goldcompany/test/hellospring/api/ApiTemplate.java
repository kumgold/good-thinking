package com.goldcompany.test.hellospring.api;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

public class ApiTemplate {
    private final ApiExecutor apiExecutor;
    private final ExchangeRateExtractor exchangeRateExtractor;

    public ApiTemplate() {
        this.apiExecutor = new HttpClientApiExecutor();
        this.exchangeRateExtractor = new ErApiExchangeRateExtractor();
    }

    public ApiTemplate(ApiExecutor apiExecutor, ExchangeRateExtractor exchangeRateExtractor) {
        this.apiExecutor = apiExecutor;
        this.exchangeRateExtractor = exchangeRateExtractor;
    }

    public BigDecimal getExchangeRate(String url) {
        return this.getExchangeRate(url, this.apiExecutor, this.exchangeRateExtractor);
    }

    public BigDecimal getExchangeRate(String url, ApiExecutor apiExecutor) {
        return this.getExchangeRate(url, apiExecutor, this.exchangeRateExtractor);
    }

    public BigDecimal getExchangeRate(String url, ExchangeRateExtractor exchangeRateExtractor) {
        return this.getExchangeRate(url, this.apiExecutor, exchangeRateExtractor);
    }

    public BigDecimal getExchangeRate(String url, ApiExecutor apiExecutor, ExchangeRateExtractor exchangeRateExtractor) {
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
