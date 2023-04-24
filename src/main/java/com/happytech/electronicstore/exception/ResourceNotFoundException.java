package com.happytech.electronicstore.exception;

import lombok.Builder;


public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(){

        super("Resource not found !!");
    }

    public ResourceNotFoundException(String message){

        super(message);
    }
}
