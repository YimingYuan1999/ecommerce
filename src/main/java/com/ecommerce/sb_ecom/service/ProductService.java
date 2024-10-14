package com.ecommerce.sb_ecom.service;

import com.ecommerce.sb_ecom.model.Product;
import com.ecommerce.sb_ecom.payload.ProductDTO;
import com.ecommerce.sb_ecom.payload.ProductReponse;

public interface  ProductService {

    ProductDTO addProduct(Long categoryId, Product product);

    ProductReponse getAllProducts();

    public ProductReponse searchByCategory(Long categoryId);

    public ProductReponse searchProductByKeyword(String keyword);

}
