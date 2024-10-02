package com.goldcompany.test.hellospring;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

abstract public class PaymentService {
    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) throws IOException {
        BigDecimal exchangeRate = getExchangeRate(currency);
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

    abstract BigDecimal getExchangeRate(String currency) throws IOException;
}
