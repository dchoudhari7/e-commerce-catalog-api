package com.e_commerce_product_catalog_api.controller;

import com.e_commerce_product_catalog_api.dtos.ProductDTO;
import com.e_commerce_product_catalog_api.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/products")
@Validated
@Tag(name = "Product", description = "The Product API for managing products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @Operation(
            summary = "Create a new Product",
            description = "Creates a new product with the specified details",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Product created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data provided")
            }
    )
    public ResponseEntity<ProductDTO> createProduct(
            @Parameter(description = "Product data to be created", required = true)
            @Valid @RequestBody ProductDTO productDTO
    ) {
        ProductDTO createdProduct = productService.createProduct(productDTO);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(
            summary = "Get paginated list of Products",
            description = "Retrieves a list of all products in the catalog",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    public ResponseEntity<Page<ProductDTO>> getAllProducts(Pageable pageable) {
        Page<ProductDTO> products = productService.getAllProducts(pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get Product by ID",
            description = "Retrieves the details of a product by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Product retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Product not found with the provided ID")
            }
    )
    public ResponseEntity<ProductDTO> getProductById(
            @Parameter(description = "ID of the product to be retrieved", required = true)
            @PathVariable("id") @Min(1) Long id
    ) {
        ProductDTO product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductDTO>> filterProducts(
            @Parameter(description = "Product name to filter") @RequestParam(required = false) String name,
            @Parameter(description = "Category name to filter") @RequestParam(required = false) String category,
            @Parameter(description = "Product description to filter") @RequestParam(required = false) String description,
            Pageable pageable) {
        Page<ProductDTO> filteredProducts = productService.filterProducts(name, category, description, pageable);
        return new ResponseEntity<Page<ProductDTO>>(filteredProducts, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing Product",
            description = "Updates the details of an existing product",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Product updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data provided"),
                    @ApiResponse(responseCode = "404", description = "Product not found with the provided ID")
            }
    )
    public ResponseEntity<ProductDTO> updateProduct(
            @Parameter(description = "ID of the product to be updated", required = true)
            @PathVariable("id") @Min(1) Long id,
            @Parameter(description = "Updated product data", required = true)
            @Valid @RequestBody ProductDTO productDTO
    ) {
        ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a Product",
            description = "Deletes a product by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Product not found with the provided ID")
            }
    )
    public ResponseEntity<String> deleteProduct(
            @Parameter(description = "ID of the product to be deleted", required = true)
            @PathVariable("id") @Min(1) Long id
    ) {
        productService.deleteProduct(id);
        return new ResponseEntity<>("Product deleted successfully!", HttpStatus.OK);
    }
}
