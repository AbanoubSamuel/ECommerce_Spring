package com.ecommerce.ecommerce.dao;

import com.ecommerce.ecommerce.entities.Order;
import com.ecommerce.ecommerce.entities.User;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface OrderDAO extends ListCrudRepository<Order, Long> {


    List<Order> findByUser(User user);


}
