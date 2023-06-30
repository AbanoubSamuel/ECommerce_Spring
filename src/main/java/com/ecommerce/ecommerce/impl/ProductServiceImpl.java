package com.ecommerce.ecommerce.impl;

import com.ecommerce.ecommerce.dao.ProductDAO;
import com.ecommerce.ecommerce.entities.Product;
import com.ecommerce.ecommerce.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductDAO productDAO;


    public ProductServiceImpl(ProductDAO productDAO)
    {
        this.productDAO = productDAO;
    }


    public List<Product> getProducts()
    {
        return productDAO.findAll();
    }
}
