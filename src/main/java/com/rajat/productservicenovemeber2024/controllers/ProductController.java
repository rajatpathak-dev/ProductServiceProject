package com.rajat.productservicenovemeber2024.controllers;

import com.rajat.productservicenovemeber2024.Services.ProductService;
import com.rajat.productservicenovemeber2024.models.Product;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/limit/{num}")
    public List<Product> getLimitedProducts(@PathVariable("num") Integer num){
        return productService.getLimitedProducts(num);
    }

    @GetMapping("/categories")
    public List<String> getAllCategories(){
        return productService.getAllCategories();
    }

    @GetMapping("/category/{categoryName}")
    public List<Product> getProductsInACategory(@PathVariable("categoryName") String categoryName){
        return productService.getAllProductsInACategory(categoryName);
    }

//    @PatchMapping("/{id}")
//    public Product updateProduct(@PathVariable("id") Long id, @RequestBody Product product){
//        return productService.updateProduct(id,product);
//    }

    @PutMapping("/{id}")
    public Product replaceproduct(@PathVariable("id") Long id , @RequestBody Product product){
        return productService.replaceProduct(id,product);
    }






}
