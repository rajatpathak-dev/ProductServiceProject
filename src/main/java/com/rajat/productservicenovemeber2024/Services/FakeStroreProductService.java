package com.rajat.productservicenovemeber2024.Services;

import com.rajat.productservicenovemeber2024.DTO.FakeStoreProductDto;
import com.rajat.productservicenovemeber2024.Exceptions.CategoryNotFoundException;
import com.rajat.productservicenovemeber2024.Exceptions.ProductNotFoundException;
import com.rajat.productservicenovemeber2024.models.Category;
import com.rajat.productservicenovemeber2024.models.Product;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service("fakestoreservice")
public class FakeStroreProductService implements ProductService {

    private RestTemplate restTemplate;

    FakeStroreProductService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }
    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException{
        FakeStoreProductDto fakeStoreProductDto = restTemplate.
                getForObject("https://fakestoreapi.com/products/" + productId,
                        FakeStoreProductDto.class
                );

        if(fakeStoreProductDto == null){
            throw new ProductNotFoundException("Product with id "+productId+" not found");
        }
        return convertFakeStoreProductToProduct(fakeStoreProductDto);
    }

    private Product convertFakeStoreProductToProduct(FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setTitle(fakeStoreProductDto.getTitle());
        Category category = new Category();
        category.setName(fakeStoreProductDto.getCategory());
        category.setDescription(fakeStoreProductDto.getDescription());
        product.setCategory(category);
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        FakeStoreProductDto[] fakeStoreProductDtos =  restTemplate.getForObject("https://fakestoreapi.com/products",FakeStoreProductDto[].class);
        List<Product> products = new ArrayList<>();
        for(FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtos){
            products.add(convertFakeStoreProductToProduct(fakeStoreProductDto));
        }
        return products;
    }

    @Override
    public List<Product> getLimitedProducts(Integer num) {
        FakeStoreProductDto[] fakeStoreProductDtos = restTemplate.getForObject("https://fakestoreapi.com/products?limit="+num,FakeStoreProductDto[].class);
       List<Product> products = new ArrayList<>();
        for(FakeStoreProductDto fakeStoreProductDto:fakeStoreProductDtos){
           products.add(convertFakeStoreProductToProduct(fakeStoreProductDto));
       }
        return products;
    }

    @Override
    public List<String> getAllCategories() {
       String[] categories = restTemplate.getForObject("https://fakestoreapi.com/products/categories",String[].class);
       return Arrays.asList(categories);
    }

    @Override
    public List<Product> getAllProductsInACategory(String categoryName) throws CategoryNotFoundException {
        FakeStoreProductDto[] fakeStoreProductDtos = restTemplate.getForObject("https://fakestoreapi.com/products/category/"+categoryName,
                                                                                FakeStoreProductDto[].class);

        if(fakeStoreProductDtos.length == 0){
            throw  new CategoryNotFoundException("categoty "+categoryName+" does not exist");
        }

        List<Product> products = new ArrayList<>();
        for(FakeStoreProductDto fakeStoreProductDto:fakeStoreProductDtos){
            products.add(convertFakeStoreProductToProduct(fakeStoreProductDto));
        }
        return products;
    }

    @Override
    public Product saveProduct(Product product) {
       FakeStoreProductDto fakeStoreProductDto = restTemplate.postForObject("https://fakestoreapi.com/products", convertProductToFakeStoreProductDto(product), FakeStoreProductDto.class);
        return convertFakeStoreProductToProduct(fakeStoreProductDto);
    }

    public FakeStoreProductDto convertProductToFakeStoreProductDto(Product product){
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();

        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setDescription(product.getCategory().getDescription());
        fakeStoreProductDto.setCategory(product.getCategory().getName());
        fakeStoreProductDto.setImage(null);

        return fakeStoreProductDto;
    }

    @Override
    public Product updateProduct(Long id,Product product) {
        ResponseEntity<FakeStoreProductDto> response = restTemplate.exchange(
                "https://fakestoreapi.com/products/" + id,
                HttpMethod.PATCH,
                new HttpEntity<>(convertProductToFakeStoreProductDto(product)), // Correctly pass the `Product` object wrapped in an HttpEntity
                FakeStoreProductDto.class  // Correctly specify the response type
        );

        return convertFakeStoreProductToProduct(response.getBody());

    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        ResponseEntity<FakeStoreProductDto> response = restTemplate.exchange(
                "https://fakestoreapi.com/products/" + id,
                HttpMethod.PUT,
                new HttpEntity<>(convertProductToFakeStoreProductDto(product)), // Correctly pass the `Product` object wrapped in an HttpEntity
                FakeStoreProductDto.class  // Correctly specify the response type
        );



        return convertFakeStoreProductToProduct(response.getBody());
    }

    @Override
    public Product deleteProduct(Long id) {

        ResponseEntity<FakeStoreProductDto> response = restTemplate.exchange(
                "https://fakestoreapi.com/products/" + id,
                HttpMethod.DELETE,
                new HttpEntity<>(null), // Correctly pass the `Product` object wrapped in an HttpEntity
                FakeStoreProductDto.class  // Correctly specify the response type
        );
        return convertFakeStoreProductToProduct(response.getBody());
    }


}
