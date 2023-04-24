package com.happytech.electronicstore.controller;

import com.happytech.electronicstore.Service.ProductService;
import com.happytech.electronicstore.config.AppConstant;
import com.happytech.electronicstore.dtos.ApiResponse;
import com.happytech.electronicstore.dtos.PagableResponse;
import com.happytech.electronicstore.dtos.ProductDto;
import com.happytech.electronicstore.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@Slf4j
@RestController
@RequestMapping("/product")

public class ProductController {
@Autowired
    private ProductService productService;

    //create
    @PostMapping("/createproduct")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto){
        log.info("Intiating request to Create Product");
        ProductDto productDto1 = this.productService.create(productDto);
        log.info("Completed request to create the Product");
        return new ResponseEntity<>(productDto1, HttpStatus.CREATED);

    }
    //update

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto,@PathVariable Long productId)
    {
        log.info("Intiating request to Update Product with productid:{}",productId);
        ProductDto productDto1 = this.productService.update(productDto,productId);
        log.info("Completed request to Update Product with productid:{}",productId);
        return new ResponseEntity<>(productDto1,HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId)
    {
        log.info("Intiating request to Delete Product with productid:{}",productId);

        this.productService.delete(productId);
        ApiResponse apiResponse =ApiResponse.builder().message(AppConstant.DELETE).status(HttpStatus.OK).success(true).build();

        log.info("Completed request to Delete Product with productid:{}",productId);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @GetMapping("/byId/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long productId)
    {
        log.info("Intiating request to Get Product with productid:{}",productId);

        ProductDto productDto = this.productService.get(productId);

        log.info("Completed request to Delete Product with productid:{}",productId);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }
    @GetMapping()
    public ResponseEntity<PagableResponse<ProductDto>> getAllProducts(
            @RequestParam(value = "pagenumber", defaultValue = "0", required = false) Integer pagenumber,
            @RequestParam(value = "pagesize", defaultValue = "10", required = false) Integer pagesize
            , @RequestParam(value = "sortBy", defaultValue = "productId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir)

    {
        log.info("Intiating request to GetAll Products");
        PagableResponse<ProductDto> products = this.productService.getAll(pagesize, pagenumber, sortBy, sortDir);
        log.info("Completed request to GetAll Products");
        return new ResponseEntity<>(products,HttpStatus.OK);
    }
    @GetMapping("/{title}")
    public ResponseEntity<PagableResponse<ProductDto>> searchbytitle(
            @PathVariable String title,
            @RequestParam(value = "pagenumber", defaultValue = "0", required = false) Integer pagenumber,
            @RequestParam(value = "pagesize", defaultValue = "10", required = false) Integer pagesize
            , @RequestParam(value = "sortBy", defaultValue = "productId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir)

    {
        log.info("Intiating request to GetAll Products by searching title:{}",title);
        PagableResponse<ProductDto> products = this.productService.searchByTitle(title,pagesize, pagenumber, sortBy, sortDir);
        log.info("Completed request to GetAll Products by searching title:{}",title);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }
    @GetMapping("/true/{live}")
    public ResponseEntity<PagableResponse<ProductDto>> searchBylive(
            @PathVariable Boolean live,
            @RequestParam(value = "pagenumber", defaultValue = "0", required = false) Integer pagenumber,
            @RequestParam(value = "pagesize", defaultValue = "10", required = false) Integer pagesize
            , @RequestParam(value = "sortBy", defaultValue = "productId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir)

    {
        log.info("Intiating request to GetAll Products by searching live:{}",live);
        PagableResponse<ProductDto> products = this.productService.getAllLive(live,pagesize, pagenumber, sortBy, sortDir);
        log.info("Intiating request to GetAll Products by searching live:{}",live);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }




}
