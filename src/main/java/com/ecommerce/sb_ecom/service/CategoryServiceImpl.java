package com.ecommerce.sb_ecom.service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.sb_ecom.exceptions.APIException;
import com.ecommerce.sb_ecom.repository.CategoryRepository;
import com.ecommerce.sb_ecom.exceptions.ResourceNotFoundException;
import com.ecommerce.sb_ecom.model.Category;
import com.ecommerce.sb_ecom.payload.CategoryDTO;
import com.ecommerce.sb_ecom.payload.CategoryReponse;
import com.sun.net.httpserver.HttpsConfigurator;

@Service
public class CategoryServiceImpl implements CategoryService{

    // private List<Category> categories = new ArrayList<>();
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryReponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") 
                                ? Sort.by(sortBy).ascending()
                                :Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);
        List<Category> categories = categoryPage.getContent();
        if (categories.isEmpty()){
            throw new APIException("No category find!");
        }
        List<CategoryDTO> categoryDTOs = categories.stream()
                                        .map(category -> modelMapper.map(category, CategoryDTO.class)).toList();
        
        
        CategoryReponse categoryReponse = new CategoryReponse();
        categoryReponse.setContent(categoryDTOs);
        categoryReponse.setPageNumber(categoryPage.getNumber());
        categoryReponse.setPageSize(categoryPage.getSize());
        categoryReponse.setTotalElements(categoryPage.getTotalElements());
        categoryReponse.setTotalPages(categoryPage.getTotalPages());
        categoryReponse.setLastPage(categoryPage.isLast());
        return categoryReponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category categoryFromDb = categoryRepository.findByCategoryName(category.getCategoryName());
        if (categoryFromDb != null){
            throw new APIException("Category with the name " + category.getCategoryName() + " already exists!");
        }
        Category savedCategory = this.categoryRepository.save(category);
        CategoryDTO savedCategoryDTO = modelMapper.map(savedCategory, CategoryDTO.class);
        return savedCategoryDTO;
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId){
        Category category = categoryRepository.findById(categoryId)
                                        .orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId",categoryId));
        
        categoryRepository.delete(category);
        CategoryDTO deletedCategoryDTO = modelMapper.map(category, CategoryDTO.class);
        return deletedCategoryDTO;
    }

    @Override
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.findById(categoryId)
                                        .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId",categoryId));
        category.setCategoryId(categoryId);
        savedCategory = categoryRepository.save(category);
        CategoryDTO savedCategoryDTO = modelMapper.map(savedCategory, CategoryDTO.class);
        return savedCategoryDTO;
    }
    
}   
