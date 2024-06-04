package com.assignment.service.Impl;

import com.assignment.dto.ProductTypeDTO;
import com.assignment.entity.ProductTypes;
import com.assignment.repository.ProductTypeRepository;
import com.assignment.service.ProductTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {

    @Autowired
    ProductTypeRepository repo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<ProductTypes> findAll() {
        return repo.findAll();
    }

    @Override
    public ProductTypes findById(Long id) {
        return repo.getReferenceById(id);
    }

    @Override
    public void insert(ProductTypeDTO productTypeDTO, Long categoryId) {
        productTypeDTO.setProductTypeId(productTypeDTO.getProductTypeId());
        productTypeDTO.setProductTypeName(productTypeDTO.getProductTypeName());
        productTypeDTO.setCategoryId(categoryId);
        productTypeDTO.setActive(Boolean.TRUE);
        ProductTypes productTypes = new ProductTypes();

        modelMapper.map(productTypeDTO, productTypes);
        repo.save(productTypes);
    }

    @Override
    public ProductTypes update(ProductTypeDTO productTypeDTO, Long categoryId) {
        productTypeDTO.setCategoryId(productTypeDTO.getProductTypeId());
        productTypeDTO.setProductTypeName(productTypeDTO.getProductTypeName());
        productTypeDTO.setCategoryId(categoryId);
        productTypeDTO.setActive(productTypeDTO.isActive());
        ProductTypes productTypes = repo.getReferenceById(productTypeDTO.getProductTypeId());

        modelMapper.map(productTypeDTO, productTypes);
        return repo.save(productTypes);
    }

    @Override
    public void delete(Long id) {
        ProductTypes productTypes = repo.getReferenceById(id);
        repo.delete(productTypes);
    }

    @Override
    public List<ProductTypes> findByCategoryId(Long categoryId) {
        return repo.findByCategoryId(categoryId);
    }
}
