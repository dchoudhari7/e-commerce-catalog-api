package com.e_commerce_product_catalog_api.service;

import com.e_commerce_product_catalog_api.dtos.ProductDTO;
import com.e_commerce_product_catalog_api.entity.Category;
import com.e_commerce_product_catalog_api.entity.FilteredProducts;
import com.e_commerce_product_catalog_api.entity.Product;
import com.e_commerce_product_catalog_api.exception.ResourceNotFoundException;
import com.e_commerce_product_catalog_api.mapper.ProductMapper;
import com.e_commerce_product_catalog_api.repository.CategoryRepository;
import com.e_commerce_product_catalog_api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductMapper productMapper;

    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }

    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(productMapper::toDTO);
    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return productMapper.toDTO(product);
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        Category existingCategory = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setStock(productDTO.getStock());
        existingProduct.setCategory(existingCategory);
        Product updatedProduct = productRepository.save(existingProduct);

        return productMapper.toDTO(updatedProduct);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
    }

    public Page<ProductDTO> filterProducts(String name, String category, String description, Pageable pageable) {
        Page<FilteredProducts> productPage = productRepository.findByFilters(name, category, description, pageable);

        return productPage.map(product -> {
            ProductDTO dto = new ProductDTO();
            dto.setId(product.getProductId());
            dto.setName(product.getProductName());
            dto.setDescription(product.getProductDescription());
            dto.setPrice(product.getPrice());
            dto.setStock(product.getStock());
            dto.setCategoryName(product.getCategoryName());
            return dto;
        });
    }
}

