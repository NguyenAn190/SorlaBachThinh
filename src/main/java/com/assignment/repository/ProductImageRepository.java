package com.assignment.repository;

import com.assignment.entity.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImages, String> {
    List<ProductImages> findByProductId(String id);
}
