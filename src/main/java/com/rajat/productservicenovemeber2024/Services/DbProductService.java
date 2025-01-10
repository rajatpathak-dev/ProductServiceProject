package com.rajat.productservicenovemeber2024.Services;

import com.rajat.productservicenovemeber2024.Exceptions.CategoryNotFoundException;
import com.rajat.productservicenovemeber2024.Exceptions.ProductNotFoundException;
import com.rajat.productservicenovemeber2024.models.Category;
import com.rajat.productservicenovemeber2024.models.Product;
import com.rajat.productservicenovemeber2024.repositories.CategoryRepo;
import com.rajat.productservicenovemeber2024.repositories.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("dbservice")
public class DbProductService implements ProductService{

    private ProductRepo productRepo;
    private CategoryRepo categoryRepo;

    public DbProductService(ProductRepo productRepo, CategoryRepo categoryRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public Product getSingleProduct(Long id) throws ProductNotFoundException {
        Optional<Product> optionalProduct = productRepo.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }
        return optionalProduct.get();
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public List<Product> getLimitedProducts(Integer num) {

        return productRepo.findLimitedProducts(num);
    }

    @Override
    public List<String> getAllCategories() {
        List<Category> categories = categoryRepo.findAll();
        List<String> categorynameList = new ArrayList<>();

        for(int i=0 ; i<categories.size() ; i++){
            categorynameList.add(categories.get(i).getName());
        }

        return categorynameList;
    }

    @Override
    public List<Product> getAllProductsInACategory(String categoryName) throws CategoryNotFoundException {
        Optional<Category> optionalCategory = getSingleCategory(categoryName);
        if(optionalCategory.isEmpty()){
            throw new CategoryNotFoundException("categoty "+categoryName+" does not exist");
        }
        return optionalCategory.get().getProducts();
    }

    @Override
    public Product saveProduct(Product product) {
        // Ensure the Category is managed
        Optional<Category> categoryOptional = getSingleCategory(product.getCategory().getName());
        if (categoryOptional.isPresent()) {
            product.setCategory(categoryOptional.get());
        } else {
            // Save the Category if it does not exist
            Category savedCategory = categoryRepo.save(product.getCategory());
            product.setCategory(savedCategory);
        }

        // Check if the product already exists
        Optional<Product> existingProduct = productRepo.findByTitleAndCategory(product.getTitle(), product.getCategory());
        if (existingProduct.isPresent()) {
            return existingProduct.get(); // Return the existing product
        }

        // Save the new product
        Product savedProduct = productRepo.save(product);
        return savedProduct;
    }


    @Override
    public Product updateProduct(Long id, Product product) throws ProductNotFoundException {
        Optional<Product> productOptional = productRepo.findById(id);

        if(productOptional.isEmpty()){
            throw new ProductNotFoundException("Product with id: " + id + " does not exist");
        }

        Product productInDb = productOptional.get();

        if(product.getTitle() != null) {
            productInDb.setTitle(product.getTitle());
        }

        if(product.getPrice() != productInDb.getPrice()) {
            productInDb.setPrice(product.getPrice());
        }

        if(product.getCategory()!= null){
            Optional<Category> categoryOptional = getSingleCategory(product.getCategory().getName());
            if (categoryOptional.isPresent()) {
                productInDb.setCategory(categoryOptional.get());
            } else {
                // Save the Category if it does not exist
                Category savedCategory = categoryRepo.save(product.getCategory());
                productInDb.setCategory(savedCategory);
            }

        }



        return productRepo.save(productInDb);
    }

    @Override
    public Product replaceProduct(Long id, Product product) throws ProductNotFoundException {
        Optional<Product> productOptional = productRepo.findById(id);

        if(productOptional.isEmpty()){
            throw new ProductNotFoundException("Product with id: " + id + " does not exist");
        }

        product.setId(id);
        Optional<Category> categoryOptional = getSingleCategory(product.getCategory().getName());
        if (categoryOptional.isPresent()) {
            product.setCategory(categoryOptional.get());
        } else {
            // Save the Category if it does not exist
            Category savedCategory = categoryRepo.save(product.getCategory());
            product.setCategory(savedCategory);
        }
        return productRepo.save(product);
    }

    @Override
    public Product deleteProduct(Long id) throws ProductNotFoundException {
        Optional<Product> productOptional = productRepo.findById(id);

        if(productOptional.isEmpty()){
            throw new ProductNotFoundException("Product with id: " + id + " does not exist");
        }

        productRepo.deleteById(id);
        return productOptional.get();
    }

    public Optional<Category> getSingleCategory(String categoryName){
        return categoryRepo.findByName(categoryName);
    }
}
