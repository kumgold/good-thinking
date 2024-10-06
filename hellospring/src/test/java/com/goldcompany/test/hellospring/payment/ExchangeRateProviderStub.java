package com.goldcompany.test.hellospring.payment;

import java.io.IOException;
import java.math.BigDecimal;

public class ExchangeRateProviderStub implements ExchangeRateProvider {
    private BigDecimal exchangeRate;

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public ExchangeRateProviderStub(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @Override
    public BigDecimal getExchangeRate(String currency) throws IOException {
        return exchangeRate;
    }
}
