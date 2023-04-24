package com.happytech.electronicstore.repository;

import com.happytech.electronicstore.dtos.PagableResponse;
import com.happytech.electronicstore.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    //search
    Page<Product> findByTitleContaining(String subTitle, Pageable pageble);

    Page<Product> findByLiveTrue(Pageable pagable);

}
