package com.goldcompany.test.hellospring.payment;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

public class PaymentService {
    private final ExchangeRateProvider webApiExchangeRateProvider;
    private final Clock clock;

    public PaymentService(ExchangeRateProvider provider, Clock clock) {
        this.webApiExchangeRateProvider = provider;
        this.clock = clock;
    }

    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) throws IOException {
        BigDecimal exchangeRate = webApiExchangeRateProvider.getExchangeRate(currency);
        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exchangeRate);
        LocalDateTime validUntil = LocalDateTime.now(clock).plusMinutes(30L);

        return new Payment(
                orderId,
                currency,
                foreignCurrencyAmount,
                exchangeRate,
                convertedAmount,
                validUntil
        );
    }
}
