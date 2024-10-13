package com.goldcompany.test.hellospring.data;

import com.goldcompany.test.hellospring.order.Order;
import com.goldcompany.test.hellospring.order.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class JpaOrderRepository implements OrderRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Order order) {
        entityManager.persist(order);
    }
}
