package com.rajat.productservicenovemeber2024.controllers;

import com.rajat.productservicenovemeber2024.Services.ProductService;
import com.rajat.productservicenovemeber2024.models.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") Long id){
        return productService.getSingleProduct(id);
    }

    @GetMapping("")
    public List<Product> getAllProducts(){
        return  productService.getAllProducts();
    }
}
