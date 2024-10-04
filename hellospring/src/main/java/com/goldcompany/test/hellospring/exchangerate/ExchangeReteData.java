package com.goldcompany.test.hellospring.exchangerate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ExchangeReteData(
        String result,
        Map<String, BigDecimal> rates
) {
}
