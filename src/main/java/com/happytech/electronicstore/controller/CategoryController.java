package com.happytech.electronicstore.controller;


import com.happytech.electronicstore.Service.CategoryService;
import com.happytech.electronicstore.Service.FileService;
import com.happytech.electronicstore.config.AppConstant;
import com.happytech.electronicstore.dtos.ApiResponse;
import com.happytech.electronicstore.dtos.CategoryDto;
import com.happytech.electronicstore.dtos.ImageResponse;
import com.happytech.electronicstore.dtos.PagableResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FileService fileService;

    @Value("${category.profile.image.path}")
    private String imageUploadPath;

    //create

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {

        log.info("Initialing the request to save the category");

        CategoryDto saveCat = categoryService.createCategory(categoryDto);

        log.info("completed the request for save category");
        return new ResponseEntity<>(saveCat, HttpStatus.CREATED);
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Long categoryId) {
        log.info("Entering the request for update the category data with categoryId {}:",categoryId);
        CategoryDto updateCat = categoryService.updateCategory(categoryDto, categoryId);
        log.info("completed the request for update the category data with categoryId {}:",categoryId);
        return new ResponseEntity<>(updateCat, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<ApiResponse> deteteCategory(@PathVariable Long categoryId) {
        log.info("Entering the request for delete the category data with categoryId {}:",categoryId);

        categoryService.deleteCategory(categoryId);

        ApiResponse deletedCat = ApiResponse.builder().message(AppConstant.CATEGORY_DELETE + categoryId).success(true).status(HttpStatus.OK).build();
        log.info("completed the request for delete the category data with categoryId {}:",categoryId);

        return new ResponseEntity<>(deletedCat, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<PagableResponse<CategoryDto>> getAllCategory(@RequestParam(value = "pagenumber", defaultValue = "0", required = false) Integer pagenumber,
                                                                       @RequestParam(value = "pagesize", defaultValue = "5", required = false) Integer pagesize,
                                                                       @RequestParam(value = "sortBy", defaultValue = "categoryId", required = false) String sortBy,
                                                                       @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir)
        {
            log.info("Entering the request for get all the category data");

            PagableResponse<CategoryDto> allCategory = categoryService.getAllCategory(pagenumber, pagesize, sortBy, sortDir);
            log.info("completed the request for get all the category data");

            return new ResponseEntity<>(allCategory, HttpStatus.OK);

        }

    @GetMapping("/singleId/{categoryId}")
    public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable Long categoryId) {
        log.info("Entering the request for get all the category data");

        CategoryDto singleCategory = categoryService.getSingleCategory(categoryId);
        log.info("completed Dao call  for single category");

        return new ResponseEntity<>(singleCategory, HttpStatus.OK);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<CategoryDto>> searchCategory(@PathVariable String keyword) {
        log.info("Initiate the request to get search categorys:{}",keyword);

        List<CategoryDto> categoryDto = categoryService.searchCategory(keyword);
        log.info("completed the request to get search categorys:{}",keyword);

        return new ResponseEntity<>(categoryDto, HttpStatus.OK);


    }

    @PostMapping("/image/{categoryId}")
    public ResponseEntity<ImageResponse> uploadImageCategory(@RequestParam("categoryImage") MultipartFile image, @PathVariable Long categoryId) throws IOException {
        log.info("Upload the image with categoryid:{}",categoryId);

        String imageName = fileService.uploadImage(image, imageUploadPath);

        CategoryDto singleCategory = categoryService.getSingleCategory(categoryId);

        singleCategory.setCoverImage(imageName);
        CategoryDto categoryDto = categoryService.updateCategory(singleCategory, categoryId);

        ImageResponse imageResponse = ImageResponse.builder().ImageName(imageName).message(AppConstant.CATEGORY_FILE_UPLOADED).success(true).status(HttpStatus.OK).build();
        log.info("Completed the upload image process",categoryId);
        return new ResponseEntity<>(imageResponse, HttpStatus.OK);
    }

    @GetMapping("/images/{categoryId}")
    public void serveImageCategory(@PathVariable Long categoryId, HttpServletResponse response) throws IOException, FileNotFoundException {

        CategoryDto singleCategory = categoryService.getSingleCategory(categoryId);

        log.info("category image name : {} ", singleCategory.getCoverImage());
        InputStream resource = fileService.getResource(imageUploadPath, singleCategory.getCoverImage());

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource, response.getOutputStream());

    }


}

