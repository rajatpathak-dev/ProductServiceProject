package com.rajat.productservicenovemeber2024.controllers;

import com.rajat.productservicenovemeber2024.Exceptions.CategoryNotFoundException;
import com.rajat.productservicenovemeber2024.Exceptions.ProductNotFoundException;
import com.rajat.productservicenovemeber2024.Services.DbProductService;
import com.rajat.productservicenovemeber2024.Services.ProductService;
import com.rajat.productservicenovemeber2024.models.Category;
import com.rajat.productservicenovemeber2024.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    public ProductController(@Qualifier("dbservice") ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getSingleProduct(@PathVariable("id") Long id) throws ProductNotFoundException {
        return new ResponseEntity<>(productService.getSingleProduct(id), HttpStatus.OK);
    }



    @GetMapping("")
    public ResponseEntity<List<Product>>  getAllProducts(){

        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/limit/{num}")
    public ResponseEntity<List<Product>>  getLimitedProducts(@PathVariable("num") Integer num){
        return new ResponseEntity<>(productService.getLimitedProducts(num), HttpStatus.OK);
    }

    @GetMapping("/category")
    public List<String> getAllCategories(){
        return productService.getAllCategories();
    }

    @GetMapping("/category/{categoryName}")
    public List<Product> getProductsInACategory(@PathVariable("categoryName") String categoryName) throws CategoryNotFoundException {
        return productService.getAllProductsInACategory(categoryName);
    }

    @PostMapping("")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product){
        return new ResponseEntity<>(productService.saveProduct(product),HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public Product updateProduct(@PathVariable("id") Long id, @RequestBody Product product) throws ProductNotFoundException {
        return productService.updateProduct(id,product);
    }

    @PutMapping("/{id}")
    public Product replaceproduct(@PathVariable("id") Long id , @RequestBody Product product) throws ProductNotFoundException {
        return productService.replaceProduct(id,product);
    }


    @DeleteMapping("/{id}")
    public Product deleteProduct(@PathVariable("id") Long id) throws ProductNotFoundException {
       return productService.deleteProduct(id);
    }


}
