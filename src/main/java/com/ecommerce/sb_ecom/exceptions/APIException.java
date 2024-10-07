package com.ecommerce.sb_ecom.exceptions;
import java.util.Objects;

public class APIException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public APIException() {
    }

    public APIException(String message) {
        super(message);
    }    
    

}
