package com.ecommerce.sb_ecom.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.ecommerce.sb_ecom.model.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.sb_ecom.service.CategoryService;
import com.sun.net.httpserver.HttpsConfigurator;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CategoryCotroller {
    private CategoryService categoryService;
    private Long nextId = 1L;

    @Autowired
    public CategoryCotroller(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/public/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List <Category> categories = categoryService.getAllCategories();
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }

    @PostMapping("/admin/categories")
    public ResponseEntity<String> createCategory(@Valid @RequestBody Category category) {
        category.setCategoryId(nextId++);
        categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body("Create a new category!!");
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        String status = categoryService.deleteCategory(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(status);
    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@PathVariable Long categoryId,
                                                @RequestBody Category category) {
        Category savedCategory = categoryService.updateCategory(categoryId, category);
        return ResponseEntity.status(HttpStatus.OK).body("Update categoryid: " + categoryId);
    }
}
