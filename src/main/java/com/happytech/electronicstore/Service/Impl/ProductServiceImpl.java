package com.happytech.electronicstore.Service.Impl;

import com.happytech.electronicstore.Service.ProductService;
import com.happytech.electronicstore.dtos.PagableResponse;
import com.happytech.electronicstore.dtos.ProductDto;
import com.happytech.electronicstore.dtos.UserDto;
import com.happytech.electronicstore.entity.Product;
import com.happytech.electronicstore.entity.User;
import com.happytech.electronicstore.payload.Sort_Helper;
import com.happytech.electronicstore.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public ProductDto create(ProductDto productDto) {
        Product product = mapper.map(productDto, Product.class);
        Product saveProduct = productRepository.save(product);
        return mapper.map(saveProduct, ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto, Long productId) {
        //fetch the product of given id
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("product id not found"));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setQuantity(productDto.getQuantity());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());

        //save the entity
        Product updatedProduct = productRepository.save(product);

        return mapper.map(updatedProduct, ProductDto.class);
    }

    @Override
    public void delete(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("product id not found"));
        productRepository.delete(product);

    }

    @Override
    public ProductDto get(Long productId) {
        Product productDto = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("product id not found"));
        ProductDto map = this.mapper.map(productDto, ProductDto.class);
        return map;
    }

    @Override
    public PagableResponse<ProductDto> getAll(Integer pagenumber, Integer pagesize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : (Sort.by(sortBy).descending());
        Pageable page = PageRequest.of(pagenumber, pagesize, sort);
        Page<Product> all = productRepository.findAll(page);
        return Sort_Helper.getPageableresponse(all, ProductDto.class);
    }

    @Override
    public PagableResponse<ProductDto> getAllLive(boolean live, Integer pagenumber, Integer pagesize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : (Sort.by(sortBy).descending());
        Pageable page = PageRequest.of(pagenumber, pagesize, sort);
        Page<Product> titleContaining = this.productRepository.findAll(page);
        PagableResponse<ProductDto> pageableresponse = Sort_Helper.getPageableresponse(titleContaining, ProductDto.class);

        return pageableresponse;
    }

    @Override
    public PagableResponse<ProductDto> searchByTitle(String subTitle, Integer pagenumber, Integer pagesize, String sortBy, String sortDir) {
        Sort sort1 = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : (Sort.by(sortBy).descending());
        PageRequest of = PageRequest.of(pagenumber, pagesize, sort1);
        Page<Product> containing = this.productRepository.findByTitleContaining(subTitle, of);
        PagableResponse<ProductDto> pageableresponse = Sort_Helper.getPageableresponse(containing, ProductDto.class);

        return pageableresponse;
    }
}

