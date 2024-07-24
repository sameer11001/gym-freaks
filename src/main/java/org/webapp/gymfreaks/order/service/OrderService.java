package org.webapp.gymfreaks.order.service;

import org.springframework.stereotype.Service;
import org.webapp.gymfreaks.order.model.Order;
import org.webapp.gymfreaks.order.repository.OrderRepository;

@Service
public class OrderService {

    OrderRepository orderRepository;

    OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order savOrder(Order order) {
        return orderRepository.save(order);
    }

}
