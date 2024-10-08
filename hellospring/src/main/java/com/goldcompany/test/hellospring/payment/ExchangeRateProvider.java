package com.goldcompany.test.hellospring.payment;

import java.math.BigDecimal;

public interface ExchangeRateProvider {
    BigDecimal getExchangeRate(String currency);
}
