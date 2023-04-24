package com.happytech.electronicstore.Service;

import com.happytech.electronicstore.dtos.CategoryDto;
import com.happytech.electronicstore.dtos.PagableResponse;

import java.util.List;

public interface CategoryService {

    //create

    CategoryDto createCategory(CategoryDto categoryDto);

    //update

    CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId);

    //delete
    void deleteCategory(Long categoryId);

    //getAll

    PagableResponse<CategoryDto> getAllCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortDir );

    //get single category details

    CategoryDto getSingleCategory(Long categoryId);

    //search
    List<CategoryDto> searchCategory(String keyword);


}
