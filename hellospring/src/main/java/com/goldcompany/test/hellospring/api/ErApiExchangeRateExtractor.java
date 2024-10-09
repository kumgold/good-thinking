package com.goldcompany.test.hellospring.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goldcompany.test.hellospring.exchangerate.ExchangeReteData;

import java.math.BigDecimal;

public class ErApiExchangeRateExtractor implements ExchangeRateExtractor {
    @Override
    public BigDecimal extract(String response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ExchangeReteData data = mapper.readValue(response, ExchangeReteData.class);
        return data.rates().get("KRW");
    }
}
