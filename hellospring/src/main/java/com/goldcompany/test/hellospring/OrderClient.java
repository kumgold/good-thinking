package com.goldcompany.test.hellospring;

import com.goldcompany.test.hellospring.order.Order;
import com.goldcompany.test.hellospring.order.OrderService;
import com.goldcompany.test.hellospring.order.OrderServiceImpl;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.orm.jpa.JpaTransactionManager;

import java.math.BigDecimal;

public class OrderClient {
    public static void main(String[] args) {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(OrderConfig.class);
        OrderService service = beanFactory.getBean(OrderService.class);
        JpaTransactionManager transactionManager = beanFactory.getBean(JpaTransactionManager.class);

        Order order = service.createOrder("0100", BigDecimal.TEN);
    }
}
