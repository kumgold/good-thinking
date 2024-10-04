package com.goldcompany.test.hellospring.payment;

import java.io.IOException;
import java.math.BigDecimal;

public interface ExchangeRateProvider {
    BigDecimal getExchangeRate(String currency) throws IOException;
}
