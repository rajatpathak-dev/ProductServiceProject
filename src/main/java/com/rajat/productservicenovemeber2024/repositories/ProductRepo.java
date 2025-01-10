package com.rajat.productservicenovemeber2024.repositories;

import com.rajat.productservicenovemeber2024.models.Category;
import com.rajat.productservicenovemeber2024.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {
   List<Product> findAllByCategory(Category category);
   @Query(value = "SELECT * FROM product LIMIT :limit", nativeQuery = true)
   List<Product> findLimitedProducts(@Param("limit") int limit);


   Optional<Product> findByTitleAndCategory(String title,Category category);
}
