package com.rajat.productservicenovemeber2024.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Category extends Base {
    private String name;
    private String description;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    List<Product> products;

}
