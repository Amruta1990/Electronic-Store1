package com.happytech.electronicstore.dtos;

import lombok.Builder;
import org.springframework.http.HttpStatus;
@Builder
public class ApiResponse {

    private String message;

    private boolean success;

    private HttpStatus status;
}
