package com.ecommerce.ecommerce.dao;

import com.ecommerce.ecommerce.entities.Product;
import org.springframework.data.repository.ListCrudRepository;

public interface ProductDAO extends ListCrudRepository<Product, Long> {

}
