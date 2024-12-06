package com.rajat.productservicenovemeber2024.Services;

import com.rajat.productservicenovemeber2024.models.Product;

import java.util.List;

public interface ProductService {
    Product getSingleProduct(Long id);
    List<Product> getAllProducts();
}
