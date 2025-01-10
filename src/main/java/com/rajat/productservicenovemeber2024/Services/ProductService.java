package com.rajat.productservicenovemeber2024.Services;

import com.rajat.productservicenovemeber2024.Exceptions.CategoryNotFoundException;
import com.rajat.productservicenovemeber2024.Exceptions.ProductNotFoundException;
import com.rajat.productservicenovemeber2024.ProductServiceNovemeber2024Application;
import com.rajat.productservicenovemeber2024.models.Product;

import java.util.List;

public interface ProductService {
    Product getSingleProduct(Long id) throws ProductNotFoundException;
    List<Product> getAllProducts();

    List<Product> getLimitedProducts(Integer num);

    List<String> getAllCategories();

    List<Product> getAllProductsInACategory(String categoryName) throws CategoryNotFoundException;

    Product saveProduct(Product product);


    Product updateProduct(Long id, Product product) throws ProductNotFoundException;

    Product replaceProduct(Long id, Product product) throws ProductNotFoundException;

    Product deleteProduct(Long id) throws ProductNotFoundException;
}
