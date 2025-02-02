package com.e_commerce_product_catalog_api.service;

import com.e_commerce_product_catalog_api.dtos.CategoryDTO;
import com.e_commerce_product_catalog_api.entity.Category;
import com.e_commerce_product_catalog_api.exception.ResourceNotFoundException;
import com.e_commerce_product_catalog_api.mapper.CategoryMapper;
import com.e_commerce_product_catalog_api.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Enables Mockito
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks // Injects mocks into the service
    private CategoryService categoryService;

    private Category category;
    private CategoryDTO categoryDTO;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Electronics");
        category.setDescription("Devices and Gadgets");

        categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setName("Electronics");
        categoryDTO.setDescription("Devices and Gadgets");
    }

    /**
     * Test: Create Category
     */
    @Test
    void testCreateCategory() {
        when(categoryMapper.toEntity(any(CategoryDTO.class))).thenReturn(category);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        when(categoryMapper.toDTO(any(Category.class))).thenReturn(categoryDTO);

        CategoryDTO result = categoryService.createCategory(categoryDTO);

        assertNotNull(result);
        assertEquals(categoryDTO.getName(), result.getName());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    /**
     * Test: Get All Categories
     */
    @Test
    void testGetAllCategories() {
        List<Category> categoryList = Arrays.asList(category);
        when(categoryRepository.findAll()).thenReturn(categoryList);
        when(categoryMapper.toDTO(any(Category.class))).thenReturn(categoryDTO);

        List<CategoryDTO> result = categoryService.getAllCategories();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(categoryRepository, times(1)).findAll();
    }

    /**
     * Test: Get Category by ID (Found)
     */
    @Test
    void testGetCategoryById_Found() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(categoryMapper.toDTO(any(Category.class))).thenReturn(categoryDTO);

        CategoryDTO result = categoryService.getCategoryById(1L);

        assertNotNull(result);
        assertEquals(categoryDTO.getId(), result.getId());
        verify(categoryRepository, times(1)).findById(1L);
    }

    /**
     * Test: Get Category by ID (Not Found)
     */
    @Test
    void testGetCategoryById_NotFound() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.getCategoryById(1L);
        });

        assertEquals("Category not found with id: 1", exception.getMessage());
        verify(categoryRepository, times(1)).findById(1L);
    }

    /**
     * Test: Update Category (Success)
     */
//    @Test
//    void testUpdateCategory_Success() {
//        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
//        when(categoryRepository.save(any(Category.class))).thenReturn(category);
//        when(categoryMapper.toDTO(any(Category.class))).thenReturn(categoryDTO);
//
//        CategoryDTO updatedDTO = new CategoryDTO();
//        updatedDTO.setName("Updated Electronics");
//        updatedDTO.setDescription("Updated description");
//
//        CategoryDTO result = categoryService.updateCategory(1L, updatedDTO);
//
//        assertNotNull(result);
//        assertEquals("Updated Electronics", result.getName());
//        verify(categoryRepository, times(1)).save(any(Category.class));
//    }

    /**
     * Test: Update Category (Not Found)
     */
    @Test
    void testUpdateCategory_NotFound() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.updateCategory(1L, categoryDTO);
        });

        assertEquals("Category not found with id: 1", exception.getMessage());
        verify(categoryRepository, times(1)).findById(1L);
    }

    /**
     * Test: Delete Category (Success)
     */
    @Test
    void testDeleteCategory_Success() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        doNothing().when(categoryRepository).delete(any(Category.class));

        categoryService.deleteCategory(1L);

        verify(categoryRepository, times(1)).delete(category);
    }

    /**
     * Test: Delete Category (Not Found)
     */
    @Test
    void testDeleteCategory_NotFound() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.deleteCategory(1L);
        });

        assertEquals("Category not found with id: 1", exception.getMessage());
        verify(categoryRepository, times(1)).findById(1L);
    }
}
