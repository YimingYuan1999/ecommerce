package com.ecommerce.sb_ecom.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.ecommerce.sb_ecom.model.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.sb_ecom.config.AppConstants;
import com.ecommerce.sb_ecom.payload.CategoryDTO;
import com.ecommerce.sb_ecom.payload.CategoryReponse;
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

    @GetMapping("/echo")
    public ResponseEntity<String> echoMessage(@RequestParam(name = "message", required=false) String message){
        return new ResponseEntity<>("Echoed message: " + message, HttpStatus.OK);
    }

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryReponse> getAllCategories(
        @RequestParam(name = "pageNumber", defaultValue= AppConstants.PAGE_NUMBER, required=false) Integer pageNumber,
        @RequestParam(name = "pageSize", defaultValue= AppConstants.PAGE_SIZE, required=false) Integer pageSize,
        @RequestParam(name = "sortBy", defaultValue= AppConstants.SORT_CATEGORIES_BY, required=false) String sortBy,
        @RequestParam(name = "sortOrder", defaultValue= AppConstants.SORT_DIR, required=false) String sortOrder
    ) {
        CategoryReponse categoryReponse = categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(categoryReponse, HttpStatus.OK);
    }

    @PostMapping("/admin/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        categoryDTO.setCategoryId(nextId++);
        CategoryDTO savedCategoryDTO = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId) {
        CategoryDTO deletedCategoryDTO = categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(deletedCategoryDTO, HttpStatus.OK);
    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long categoryId,
                                                @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO savedCategoryDTO = categoryService.updateCategory(categoryId, categoryDTO);
        // return ResponseEntity.status(HttpStatus.OK).body("Update categoryid: " + categoryId);
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.OK);
    }
}
