package com.happytech.electronicstore.Service.Impl;

import com.happytech.electronicstore.Service.CategoryService;
import com.happytech.electronicstore.config.AppConstant;
import com.happytech.electronicstore.dtos.CategoryDto;
import com.happytech.electronicstore.dtos.PagableResponse;
import com.happytech.electronicstore.entity.Category;
import com.happytech.electronicstore.exception.ResourceNotFoundException;
import com.happytech.electronicstore.payload.Sort_Helper;
import com.happytech.electronicstore.repository.CategoryRepo;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;




@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Value(AppConstant.CATEGORY_VALUE)
    private String imageUploadPath;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        log.info("Initiate the dao class to create the category");

        Category category = this.modelMapper.map(categoryDto, Category.class);

        Category saveCat = categoryRepo.save(category);

        CategoryDto savecategoryDto = this.modelMapper.map(saveCat, CategoryDto.class);
        log.info("completed the dao class to create the category");
        return savecategoryDto;


    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
//get category of given id
        //get category of given id
        log.info("Initiate the dao call to update the category:{}",categoryId);
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstant.CATEGORY));

        //update category
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());

        Category updatedCat = categoryRepo.save(category);

        log.info("complete the dao call to update the category:{}",categoryId);
        return this.modelMapper.map(updatedCat, CategoryDto.class);
    }


    @Override
    public void deleteCategory(Long categoryId) {
        log.info("Initiate the dao call to delete the category:{}",categoryId);

        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException((AppConstant.CATEGORY)));

        //delete category profile image
        //images/category/abc.png  (is tarah path milelga)

        String fullPath = imageUploadPath + category.getCoverImage();
        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        } catch (NoSuchFileException ex) {
            log.info("Category image not found in folder....!!");
            ex.printStackTrace();
        } catch (IOException io) {
            io.printStackTrace();
        }

        category.setIsactive(AppConstant.NO);

        categoryRepo.delete(category);
        log.info("completed the dao call to delete the category:{}",categoryId);

    }


    @Override
    public PagableResponse<CategoryDto> getAllCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        log.info("Initiate the dao call to get All the categorys:{}");

        Sort sort = (sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending());
        Pageable page = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> getpage = categoryRepo.findAll(page);

      //  getpage.stream().filter(c -> c.getIsactive().equals(AppConstant.YES)).collect(Collectors.toList());

       // PagableResponse<CategoryDto> pagebleResponse = Sort_Helper.getPagebleResponse(getpage, CategoryDto.class);
        PagableResponse<CategoryDto> pageableresponse = Sort_Helper.getPageableresponse(getpage, CategoryDto.class);
        log.info("completed the dao call to get All the categorys:{}");
        return pageableresponse;
    }


    @Override
    public CategoryDto getSingleCategory(Long categoryId) {
        log.info("Initiate the dao call to get single categorys:{}",categoryId);
        Category singleCat = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.CATEGORY));

        CategoryDto singleCategory = this.modelMapper.map(singleCat, CategoryDto.class);
        log.info("completed the dao call to get single categorys:{}",categoryId);
        return singleCategory;
    }


    @Override
    public List<CategoryDto> searchCategory(String keyword) {
        log.info("Initiate the dao call to get search categorys:{}",keyword);
        List<Category> category = (List<Category>) categoryRepo.findByTitleContaining(keyword);

        List<CategoryDto> searchCat = category.stream().map((cat) -> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
        log.info("completed the dao call to get search categorys:{}",keyword);
        return searchCat;


    }
}
