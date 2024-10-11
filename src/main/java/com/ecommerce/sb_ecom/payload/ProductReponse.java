package com.ecommerce.sb_ecom.payload;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductReponse {
    private List<ProductDTO> content;

}
