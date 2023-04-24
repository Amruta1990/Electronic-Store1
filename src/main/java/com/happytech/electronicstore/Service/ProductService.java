package com.happytech.electronicstore.Service;

import com.happytech.electronicstore.dtos.PagableResponse;
import com.happytech.electronicstore.dtos.ProductDto;
import com.happytech.electronicstore.entity.Product;

public interface ProductService {
    //create
    ProductDto create(ProductDto productDto);

    //update
    ProductDto update(ProductDto productDto, Long productId);

    //delete
    void delete(Long productId);
    //get single
    ProductDto get(Long productId);

    //get all
    PagableResponse<ProductDto> getAll(Integer pagenumber, Integer pagesize, String sortBy, String sortDir);

    //get all : live


    PagableResponse<ProductDto> getAllLive(boolean live,Integer pagenumber, Integer pagesize, String sortBy, String sortDir);

    //search product



    PagableResponse<ProductDto> searchByTitle(String subTitle,Integer pagenumber, Integer pagesize, String sortBy, String sortDir);

    //others




}
