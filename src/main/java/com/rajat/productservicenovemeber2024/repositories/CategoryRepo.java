package com.rajat.productservicenovemeber2024.repositories;

import com.rajat.productservicenovemeber2024.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Long> {

    Optional<Category> findByName(String name);
}
