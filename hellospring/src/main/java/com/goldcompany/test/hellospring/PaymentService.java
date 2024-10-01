package com.goldcompany.test.hellospring;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class PaymentService {
    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) throws IOException {
        URL url = new URL("https://open.er-api.com/v6/latest/" + currency);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String response = br.lines().collect(Collectors.joining());
        br.close();

        ObjectMapper mapper = new ObjectMapper();
        ExchangeReteData data = mapper.readValue(response, ExchangeReteData.class);
        BigDecimal exchangeRate = data.rates().get("KRW");
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

    public static void main(String[] args) throws IOException {
        PaymentService paymentService = new PaymentService();
        Payment payment = paymentService.prepare(100L, "USD", BigDecimal.valueOf(120.8));
        System.out.println(payment);
    }
}
