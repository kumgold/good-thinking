package com.goldcompany.test.hellospring.order;

import com.goldcompany.test.hellospring.OrderConfig;
import com.goldcompany.test.hellospring.TestPaymentConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = OrderConfig.class)
public class OrderServiceSpringTest {
    @Autowired
    OrderService orderService;

    @Test
    void createOrder() {
        var order = orderService.createOrder("1111", BigDecimal.TWO);

        Assertions.assertThat(order.getId()).isNotNull();
    }
}
