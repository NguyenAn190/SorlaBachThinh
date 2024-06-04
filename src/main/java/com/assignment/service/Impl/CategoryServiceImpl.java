package com.assignment.service.Impl;

import com.assignment.dto.CategoryDTO;
import com.assignment.entity.Categorys;
import com.assignment.repository.CategoryRepository;
import com.assignment.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository repo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<Categorys> findAll() {
        return repo.findAll();
    }

    @Override
    public List<Categorys> findAllTop4(Pageable pageable) {
        return repo.findTop4Categories(pageable);
    }

    @Override
    public Page<Categorys> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public Categorys findById(Long id) {
        return repo.getReferenceById(id);
    }

    @Override
    public void insert(CategoryDTO categoryDTO, String file) {
        categoryDTO.setCategoryId(categoryDTO.getCategoryId());
        categoryDTO.setCategoryName(categoryDTO.getCategoryName());
        categoryDTO.setCategoryImage(file);
        categoryDTO.setActive(Boolean.TRUE);
        Categorys categorys = new Categorys();

        modelMapper.map(categoryDTO, categorys);
        repo.save(categorys);
    }

    @Override
    public void update(CategoryDTO categoryDTO, String file) {
        categoryDTO.setCategoryId(categoryDTO.getCategoryId());
        categoryDTO.setCategoryName(categoryDTO.getCategoryName());
        categoryDTO.setCategoryImage(file);
        categoryDTO.setActive(categoryDTO.isActive());
        Categorys categorys = repo.getReferenceById(categoryDTO.getCategoryId());

        modelMapper.map(categoryDTO, categorys);
        repo.save(categorys);
    }

    @Override
    public void delete(Long id) {
        Categorys categorys = repo.getReferenceById(id);
        repo.delete(categorys);
    }

    @Override
    public Categorys findByCategoryId(long categoryId) {
        return repo.findByCategoryId(categoryId);
    }
}
