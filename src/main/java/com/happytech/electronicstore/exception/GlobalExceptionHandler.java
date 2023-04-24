package com.happytech.electronicstore.exception;

import com.happytech.electronicstore.payload.ApiResponseMessage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //handler resource not found exception

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseMessage> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){

         //logger.info("")
        ApiResponseMessage response = ApiResponseMessage.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).success(true).build();
        return new ResponseEntity(response,HttpStatus.NOT_FOUND);

    }
    //handle bad Api Exception
 @ExceptionHandler(BadApiRequest.class)
    public  ResponseEntity<ApiResponseMessage> handleBadApiRequest(BadApiRequest ex){
        log.info("Bad Api Request");
        ApiResponseMessage response = ApiResponseMessage.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).success(false).build();
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);


    }
}
