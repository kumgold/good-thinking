package com.goldcompany.test.hellospring.payment;

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

    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) {
        BigDecimal exchangeRate = webApiExchangeRateProvider.getExchangeRate(currency);

        return Payment.create(orderId, currency, foreignCurrencyAmount, exchangeRate, LocalDateTime.now(this.clock));
    }
}
