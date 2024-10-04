package com.goldcompany.test.hellospring.exchangerate;

import com.goldcompany.test.hellospring.payment.ExchangeRateProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CachedExchangeRateProvider implements ExchangeRateProvider {
    private final ExchangeRateProvider provider;

    private BigDecimal cachedExchangeRate;
    private LocalDateTime cacheExpiryTime;

    public CachedExchangeRateProvider(ExchangeRateProvider provider) {
        this.provider = provider;
    }

    @Override
    public BigDecimal getExchangeRate(String currency) throws IOException {
        if (cachedExchangeRate == null || cacheExpiryTime.isBefore(LocalDateTime.now())) {
            cachedExchangeRate = this.provider.getExchangeRate(currency);
            cacheExpiryTime = LocalDateTime.now().plusSeconds(3);

            System.out.println("Cache Updated");
        }

        return cachedExchangeRate;
    }
}
