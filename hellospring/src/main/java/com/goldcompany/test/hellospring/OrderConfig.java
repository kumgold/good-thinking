package com.goldcompany.test.hellospring;

import com.goldcompany.test.hellospring.data.JdbcOrderRepository;
import com.goldcompany.test.hellospring.order.OrderRepository;
import com.goldcompany.test.hellospring.order.OrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.support.JdbcTransactionManager;

import javax.sql.DataSource;

@Configuration
@Import(DataConfig.class)
public class OrderConfig {
    @Bean
    public OrderService orderService(
            JdbcTransactionManager transactionManager,
            OrderRepository orderRepository
    ) {
        return new OrderService(orderRepository, transactionManager);
    }

    @Bean
    public OrderRepository orderRepository(DataSource dataSource) {
        return new JdbcOrderRepository(dataSource);
    }
}
