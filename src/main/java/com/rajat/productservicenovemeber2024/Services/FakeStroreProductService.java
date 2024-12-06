package com.rajat.productservicenovemeber2024.Services;

import com.rajat.productservicenovemeber2024.DTO.FakeStoreProductDto;
import com.rajat.productservicenovemeber2024.models.Category;
import com.rajat.productservicenovemeber2024.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStroreProductService implements ProductService {

    private RestTemplate restTemplate;

    FakeStroreProductService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }
    @Override
    public Product getSingleProduct(Long productId) {
        FakeStoreProductDto fakeStoreProductDto = restTemplate.
                getForObject("https://fakestoreapi.com/products/" + productId,
                        FakeStoreProductDto.class
                );
        return convertFakeStoreProductToProduct(fakeStoreProductDto);
    }

    private Product convertFakeStoreProductToProduct(FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setCategory(new Category(fakeStoreProductDto.getCategory(), fakeStoreProductDto.getDescription()));
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        FakeStoreProductDto[] fakeStoreProductDtos =  restTemplate.getForObject("https://fakestoreapi.com/products?sort=desc",FakeStoreProductDto[].class);
        List<Product> products = new ArrayList<>();
        for(FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtos){
            products.add(convertFakeStoreProductToProduct(fakeStoreProductDto));
        }
        return products;
    }
}
