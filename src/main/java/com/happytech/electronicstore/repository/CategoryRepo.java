package com.happytech.electronicstore.repository;

import com.happytech.electronicstore.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category,Long> {

        List<Category> findByTitleContaining(String Keywords);


}

