package com.e_commerce_product_catalog_api.mapper;

import com.e_commerce_product_catalog_api.dtos.CategoryDTO;
import com.e_commerce_product_catalog_api.entity.Category;
import com.e_commerce_product_catalog_api.repository.CategoryRepository;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.e_commerce_product_catalog_api.exception.ResourceNotFoundException;

@Component
public class CategoryMapper {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category toEntity(CategoryDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        return category;
    }

    public CategoryDTO toDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        return dto;
    }

    @Named("categoryFromId")
    public Category categoryFromId(Long categoryId) {
        if (categoryId == null) {
            return null;
        }
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + categoryId));
    }
}

