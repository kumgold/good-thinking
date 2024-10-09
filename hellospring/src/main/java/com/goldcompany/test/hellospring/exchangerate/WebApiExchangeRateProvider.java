package com.goldcompany.test.hellospring.exchangerate;

import com.goldcompany.test.hellospring.api.ApiTemplate;
import com.goldcompany.test.hellospring.api.ErApiExchangeRateExtractor;
import com.goldcompany.test.hellospring.api.HttpClientApiExecutor;
import com.goldcompany.test.hellospring.api.SImpleApiExecutor;
import com.goldcompany.test.hellospring.payment.ExchangeRateProvider;

import java.math.BigDecimal;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WebApiExchangeRateProvider implements ExchangeRateProvider {
    ApiTemplate apiTemplate = new ApiTemplate();

    @Override
    public BigDecimal getExchangeRate(String currency) {
        String url = "https://open.er-api.com/v6/latest/" + currency;

        return apiTemplate.getExchangeRate(url, new HttpClientApiExecutor(), new ErApiExchangeRateExtractor());
    }
}
