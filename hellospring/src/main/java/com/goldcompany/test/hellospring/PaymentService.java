package com.goldcompany.test.hellospring;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentService {
    private final WebApiExchangeRateProvider webApiExchangeRateProvider;

    public PaymentService() {
        this.webApiExchangeRateProvider = new WebApiExchangeRateProvider();
    }

    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) throws IOException {
        BigDecimal exchangeRate = webApiExchangeRateProvider.getExchangeRate(currency);
        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exchangeRate);
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30L);

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
