package com.ecommerce.sb_ecom.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.sb_ecom.model.Product;
import com.ecommerce.sb_ecom.payload.ProductDTO;
import com.ecommerce.sb_ecom.payload.ProductReponse;

public interface  ProductService {

    ProductDTO addProduct(Long categoryId, ProductDTO productDTO);

    ProductReponse getAllProducts();

    public ProductReponse searchByCategory(Long categoryId);

    public ProductReponse searchProductByKeyword(String keyword);

    ProductDTO updateCategory(Long productId, ProductDTO productDTO);

    ProductDTO deleteProduct(Long productId);

    ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;

}
