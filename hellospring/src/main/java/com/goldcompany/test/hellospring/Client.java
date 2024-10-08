package com.goldcompany.test.hellospring;

import com.goldcompany.test.hellospring.payment.Payment;
import com.goldcompany.test.hellospring.payment.PaymentService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;

public class Client {
    public static void main(String[] args) {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(PaymentConfig.class);
        PaymentService paymentService = beanFactory.getBean(PaymentService.class);
        Payment payment = paymentService.prepare(100L, "USD", BigDecimal.valueOf(120.8));
        System.out.println(payment);
    }
}
