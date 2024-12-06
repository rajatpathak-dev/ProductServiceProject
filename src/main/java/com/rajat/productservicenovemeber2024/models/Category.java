package com.rajat.productservicenovemeber2024.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category {
    private String name;
    private String description;

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
