package com.ecommerce.sb_ecom.controller;

import java.util.ArrayList;
import java.util.List;
import com.ecommerce.sb_ecom.model.Category;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryCotroller {
    private List<Category> categories = new ArrayList<>();

    @GetMapping("/api/public/categories")
    public List<Category> getAllCategories(){
        return categories;
    }

}
