package com.ecommerce.sb_ecom.repository;

import java.util.List;

import com.ecommerce.sb_ecom.model.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.sb_ecom.model.Category;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

    public List<Product> findByCategoryOrderByPriceAsc(Category category);

    public List<Product> findByProductNameLikeIgnoreCase(String keyword);


}
