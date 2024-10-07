package com.ecommerce.sb_ecom.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    String resourceName;
    String filed;
    String filedName;
    Long filedId;

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String resourceName, String filed, String filedName) {
        super(String.format("%s not found with %s: %s", resourceName, filed, filedName));
        this.resourceName = resourceName;
        this.filed = filed;
        this.filedName = filedName;
    }

    public ResourceNotFoundException(String resourceName, String filed, Long filedId) {
        super(String.format("%s not found with %s: %d", resourceName, filed, filedId));
        this.resourceName = resourceName;
        this.filed = filed;
        this.filedId = filedId;
    }

}
