package com.assignment.service;

import com.assignment.dto.CategoryDTO;
import com.assignment.entity.Categorys;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    List<Categorys> findAll();

    List<Categorys> findAllTop4(Pageable pageable);

    Page<Categorys> findAll(Pageable pageable);

    Categorys findById(Long id);

    void insert(CategoryDTO categoryDTO, String file);

    void update(CategoryDTO categoryDTO, String file);

    void delete(Long id);

    Categorys findByCategoryId(long categoryId);
}
