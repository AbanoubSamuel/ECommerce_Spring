package com.ecommerce.ecommerce.impl;

import com.ecommerce.ecommerce.dao.OrderDAO;
import com.ecommerce.ecommerce.entities.Order;
import com.ecommerce.ecommerce.entities.User;
import com.ecommerce.ecommerce.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderDAO orderDAO;

    public OrderServiceImpl(OrderDAO orderDAO)
    {
        this.orderDAO = orderDAO;
    }


    public List<Order> getOrders(User user)
    {
        return orderDAO.findByUser(user);
    }
}
