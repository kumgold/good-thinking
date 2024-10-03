package com.goldcompany.test.hellospring;

public class ObjectFactory {
    public PaymentService paymentService() {
        return new PaymentService(exchangeRateProvider());
    }

    public ExchangeRateProvider exchangeRateProvider() {
        return new WebApiExchangeRateProvider();
    }
}
