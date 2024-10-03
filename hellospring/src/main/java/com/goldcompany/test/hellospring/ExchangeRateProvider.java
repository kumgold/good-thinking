package com.goldcompany.test.hellospring;

import java.io.IOException;
import java.math.BigDecimal;

public interface ExchangeRateProvider {
    BigDecimal getExchangeRate(String currency) throws IOException;
}
