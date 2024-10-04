package com.goldcompany.test.hellospring.exchangerate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goldcompany.test.hellospring.payment.ExchangeRateProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public class WebApiExchangeRateProvider implements ExchangeRateProvider {
    @Override
    public BigDecimal getExchangeRate(String currency) throws IOException {
        {
            URL url = new URL("https://open.er-api.com/v6/latest/" + currency);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = br.lines().collect(Collectors.joining());
            br.close();

            ObjectMapper mapper = new ObjectMapper();
            ExchangeReteData data = mapper.readValue(response, ExchangeReteData.class);
            return data.rates().get("KRW");
        }
    }
}
