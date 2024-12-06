package com.rajat.productservicenovemeber2024.Services;

import com.rajat.productservicenovemeber2024.models.Product;

import java.util.List;

public interface ProductService {
    Product getSingleProduct(Long id);
    List<Product> getAllProducts();

    List<Product> getLimitedProducts(Integer num);

    List<String> getAllCategories();

    List<Product> getAllProductsInACategory(String categoryName);
}
