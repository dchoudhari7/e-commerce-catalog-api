package com.e_commerce_product_catalog_api.controller;

import com.e_commerce_product_catalog_api.dtos.CategoryDTO;
import com.e_commerce_product_catalog_api.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/categories")
@Validated
@Tag(name = "Category", description = "The Category API")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Create Category
    @PostMapping
    @Operation(
            summary = "Create a new Category",
            description = "Create a new Category",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Category created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    public ResponseEntity<CategoryDTO> createCategory(@Parameter(description = "Category data to be created", required = true)
                                                          @Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    // Get All Categories
    @GetMapping
    @Operation(
            summary = "Get all categories",
            description = "Get all categories",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Category retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Category not found")
            }
    )
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    // Get Category by ID
    @GetMapping("/{id}")
    @Operation(
            summary = "Get Category by ID",
            description = "Retrieve a category by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Category retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Category not found")
            }
    )
    public ResponseEntity<CategoryDTO> getCategoryById(@Parameter(description = "ID of the category to be retrieved", required = true)
                                                           @PathVariable("id") @Min(1) Long id) {
        CategoryDTO category = categoryService.getCategoryById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    // Update Category
    @PutMapping("/{id}")
    @Operation(
            summary = "Update category by ID",
            description = "Updates category by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Category updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Category not found")
            }
    )
    public ResponseEntity<CategoryDTO> updateCategory(@Parameter(description = "ID of the category to be updated", required = true)
                                                          @PathVariable("id") @Min(1) Long id, @Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO updatedCategory = categoryService.updateCategory(id, categoryDTO);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    // Delete Category
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete the category by ID",
            description = "Deletes the category by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Category deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Category not found")
            }
    )
    public ResponseEntity<String> deleteCategory(@Parameter(description = "ID of the category to be deleted", required = true)
                                                     @PathVariable("id") @Min(1) Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>("Category deleted successfully!", HttpStatus.OK);
    }
}
