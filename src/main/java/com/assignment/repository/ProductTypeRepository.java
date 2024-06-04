package com.assignment.repository;

import com.assignment.entity.ProductTypes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductTypeRepository extends JpaRepository<ProductTypes, Long> {
    List<ProductTypes> findByCategoryId(Long categoryId);
}
