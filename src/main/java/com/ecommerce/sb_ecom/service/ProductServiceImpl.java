package com.ecommerce.sb_ecom.service;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.sb_ecom.exceptions.ResourceNotFoundException;
import com.ecommerce.sb_ecom.model.Product;
import com.ecommerce.sb_ecom.payload.CategoryDTO;
import com.ecommerce.sb_ecom.payload.ProductDTO;
import com.ecommerce.sb_ecom.repository.CategoryRepository;
import com.ecommerce.sb_ecom.repository.ProductRepository;
import com.ecommerce.sb_ecom.model.Category;
import com.ecommerce.sb_ecom.payload.ProductReponse;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        Product product = modelMapper.map(productDTO, Product.class);
        product.setImage("default");
        product.setCategory(category);
        double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
        product.setSpecialPrice(specialPrice);
        Product saveProduct = productRepository.save(product);
        return modelMapper.map(saveProduct,ProductDTO.class);
    }

    @Override
    public ProductReponse getAllProducts() {
        List<Product> products =  productRepository.findAll();
        List<ProductDTO> productDTOs = products.stream().map(product->modelMapper.map(product, ProductDTO.class)).toList();
        ProductReponse productReponse = new ProductReponse();
        productReponse.setContent(productDTOs);
        return productReponse;
    }

    @Override
    public ProductReponse searchByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        List<Product> products = productRepository.findByCategoryOrderByPriceAsc(category);
        List<ProductDTO> productDTOs = products.stream().map(product->modelMapper.map(product, ProductDTO.class)).toList();
        ProductReponse productReponse = new ProductReponse();
        productReponse.setContent(productDTOs);
        return productReponse;
    }

    @Override
    public ProductReponse searchProductByKeyword(String keyword) {
        // Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        List<Product> products = productRepository.findByProductNameLikeIgnoreCase('%'+ keyword + '%');
        List<ProductDTO> productDTOs = products.stream().map(product->modelMapper.map(product, ProductDTO.class)).toList();
        ProductReponse productReponse = new ProductReponse();
        productReponse.setContent(productDTOs);
        return productReponse;
    }

    @Override
    public ProductDTO updateCategory(Long productId, ProductDTO productDTO) {
        Product productFromDb = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product", "productId", productId));
        Product product = modelMapper.map(productDTO, Product.class);
        productFromDb.setProductName(product.getProductName());
        productFromDb.setDescription(product.getDescription());
        productFromDb.setQuantity(product.getQuantity());
        productFromDb.setDiscount(product.getDiscount());
        productFromDb.setPrice(product.getPrice());
        productFromDb.setSpecialPrice(product.getSpecialPrice());
        Product savedProduct = productRepository.save(productFromDb);

        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product", "productId", productId));
        productRepository.delete(product);
        ProductDTO deleteProductDTO = modelMapper.map(product, ProductDTO.class);
        return deleteProductDTO;
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        Product productFromDb = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product","productId",productId));
        // String path = "images";
        String filename = fileService.uploadImage(path,image);

        productFromDb.setImage(filename);

        Product updateProduct = productRepository.save(productFromDb);
        // TODO Auto-generated method stub
        return modelMapper.map(updateProduct, ProductDTO.class);
    }

}
