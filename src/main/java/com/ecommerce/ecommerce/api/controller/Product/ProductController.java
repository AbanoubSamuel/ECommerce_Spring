package com.ecommerce.ecommerce.api.controller.Product;

import com.ecommerce.ecommerce.api.model.JsonResponse;
import com.ecommerce.ecommerce.entities.Product;
import com.ecommerce.ecommerce.entities.User;
import com.ecommerce.ecommerce.impl.ProductServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService)
    {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<JsonResponse<List<Product>>> getProducts(@AuthenticationPrincipal User user)
    {
        List<Product> products = productService.getProducts();
        JsonResponse<List<Product>> jsonResponse = new JsonResponse<>();
        jsonResponse.setStatus(true);
        jsonResponse.setMessage("Products orders successfully");
        jsonResponse.setData(products);
        return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
    }
}
