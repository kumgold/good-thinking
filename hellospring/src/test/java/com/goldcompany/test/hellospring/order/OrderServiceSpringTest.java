package com.goldcompany.test.hellospring.order;

import com.goldcompany.test.hellospring.OrderConfig;
import com.goldcompany.test.hellospring.TestPaymentConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = OrderConfig.class)
public class OrderServiceSpringTest {
    @Autowired
    OrderService orderService;
    @Autowired
    DataSource dataSource;

    @Test
    void createOrder() {
        var order = orderService.createOrder("1111", BigDecimal.TWO);

        Assertions.assertThat(order.getId()).isNotNull();
    }

    @Test
    void createOrders() {
        var list = List.of(
                new OrderReq("2000", BigDecimal.ONE),
                new OrderReq("2001", BigDecimal.TWO)
        );

        var orders = orderService.createOrders(list);

        Assertions.assertThat(orders).hasSize(2);
        orders.forEach( order -> {
            Assertions.assertThat(order.getId()).isNotNull();
        });
    }

    @Test
    void createDuplicatedOrders() {
        var list = List.of(
                new OrderReq("3000", BigDecimal.ONE),
                new OrderReq("3000", BigDecimal.TWO)
        );

        Assertions.assertThatThrownBy(() -> orderService.createOrders(list))
                .isInstanceOf(DataIntegrityViolationException.class);

        JdbcClient client = JdbcClient.create(dataSource);
        var count = client.sql("select count(*) from orders where no = '3000'").query(Long.class).single();
        Assertions.assertThat(count).isEqualTo(0);
    }
}
