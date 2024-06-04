package com.assignment.repository;

import com.assignment.entity.Brands;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandsRepository extends JpaRepository<Brands, String> {

    List<Brands> findAllByOrderByBrandIdDesc();
}
