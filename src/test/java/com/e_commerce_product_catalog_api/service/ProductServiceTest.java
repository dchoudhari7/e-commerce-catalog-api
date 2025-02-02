package com.e_commerce_product_catalog_api.service;

import com.e_commerce_product_catalog_api.dtos.ProductDTO;
import com.e_commerce_product_catalog_api.entity.Category;
import com.e_commerce_product_catalog_api.entity.Product;
import com.e_commerce_product_catalog_api.exception.ResourceNotFoundException;
import com.e_commerce_product_catalog_api.mapper.ProductMapper;
import com.e_commerce_product_catalog_api.repository.CategoryRepository;
import com.e_commerce_product_catalog_api.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductDTO productDTO;
    private Product product1;
    private Product product2;
    private ProductDTO productDTO1;
    private ProductDTO productDTO2;
    private Pageable pageable;
    private Page<Product> productPage;
    private Category category;

    /**
     * Sets up test data before each test method.
     */
    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Electronics");

        product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setDescription("High-performance laptop");
        product.setPrice(new BigDecimal("1200.00"));
        product.setStock(10);
        product.setCategory(category);

        productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Laptop");
        productDTO.setDescription("High-performance laptop");
        productDTO.setPrice(new BigDecimal("1200.00"));
        productDTO.setStock(10);
        productDTO.setCategoryId(1L);

        product1 = new Product();
        product1.setId(1L);
        product1.setName("Laptop");
        product1.setPrice(BigDecimal.valueOf(1200.50));

        product2 = new Product();
        product2.setId(2L);
        product2.setName("Smartphone");
        product2.setPrice(BigDecimal.valueOf(699.99));

        productDTO1 = new ProductDTO();
        productDTO1.setId(1L);
        productDTO1.setName("Laptop");
        productDTO1.setPrice(BigDecimal.valueOf(1200.50));

        productDTO2 = new ProductDTO();
        productDTO2.setId(2L);
        productDTO2.setName("Smartphone");
        productDTO2.setPrice(BigDecimal.valueOf(699.99));

        pageable = PageRequest.of(0, 2, Sort.by("name").ascending());
        productPage = new PageImpl<>(List.of(product1, product2), pageable, 2);
    }

    /**
     * Test: Create Product
     *
     * Ensures that a product is created successfully.
     */
    @Test
    void testCreateProduct() {
        when(productMapper.toEntity(any(ProductDTO.class))).thenReturn(product);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toDTO(any(Product.class))).thenReturn(productDTO);

        ProductDTO result = productService.createProduct(productDTO);

        assertNotNull(result);
        assertEquals(productDTO.getName(), result.getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    /**
     * Test: getAllProducts should return paginated list of ProductDTO
     *
     * Ensures that all products are retrieved successfully.
     */
    @Test
    void testGetAllProducts_ShouldReturnPaginatedProductDTOs() {
        // Mock repository method
        when(productRepository.findAll(pageable)).thenReturn(productPage);

        when(productMapper.toDTO(product1)).thenReturn(productDTO1);
        when(productMapper.toDTO(product2)).thenReturn(productDTO2);

        // Call service method
        Page<ProductDTO> result = productService.getAllProducts(pageable);

        // Verify repository was called
        verify(productRepository, times(1)).findAll(pageable);

        // Assert correct data transformation
        assertEquals(2, result.getTotalElements());
        assertEquals(1L, result.getContent().get(0).getId());
        assertEquals("Laptop", result.getContent().get(0).getName());
        assertEquals(1200.50, result.getContent().get(0).getPrice().doubleValue());

        assertEquals(2L, result.getContent().get(1).getId());
        assertEquals("Smartphone", result.getContent().get(1).getName());
        assertEquals(699.99, result.getContent().get(1).getPrice().doubleValue());
    }

    /**
     * Test: Get Product by ID (Found)
     *
     * Ensures that a product is returned when the ID exists.
     */
    @Test
    void testGetProductById_Found() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(productMapper.toDTO(any(Product.class))).thenReturn(productDTO);

        ProductDTO result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals(productDTO.getId(), result.getId());
        verify(productRepository, times(1)).findById(1L);
    }

    /**
     * Test: Get Product by ID (Not Found)
     *
     * Ensures that an exception is thrown when the product does not exist.
     */
    @Test
    void testGetProductById_NotFound() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            productService.getProductById(1L);
        });

        assertEquals("Product not found with id: 1", exception.getMessage());
        verify(productRepository, times(1)).findById(1L);
    }

    /**
     * Test: Update Product (Success)
     *
     * Ensures that a product is updated successfully when it exists.
     */
//    @Test
//    void testUpdateProduct_Success() {
//        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
//        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
//        when(productRepository.save(any(Product.class))).thenReturn(product);
//        when(productMapper.toDTO(any(Product.class))).thenReturn(productDTO);
//
//        ProductDTO updatedDTO = new ProductDTO();
//        updatedDTO.setName("Updated Laptop");
//        updatedDTO.setDescription("Updated description");
//        updatedDTO.setPrice(new BigDecimal("1500.00"));
//        updatedDTO.setStock(5);
//        updatedDTO.setCategoryId(1L);
//
//        ProductDTO result = productService.updateProduct(1L, updatedDTO);
//
//        assertNotNull(result);
//        assertEquals("Updated Laptop", result.getName());
//        verify(productRepository, times(1)).save(any(Product.class));
//    }

    /**
     * Test: Update Product (Product Not Found)
     *
     * Ensures that an exception is thrown when the product does not exist.
     */
    @Test
    void testUpdateProduct_ProductNotFound() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            productService.updateProduct(1L, productDTO);
        });

        assertEquals("Product not found with id: 1", exception.getMessage());
        verify(productRepository, times(1)).findById(1L);
    }

    /**
     * Test: Update Product (Category Not Found)
     *
     * Ensures that an exception is thrown when the category does not exist.
     */
    @Test
    void testUpdateProduct_CategoryNotFound() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            productService.updateProduct(1L, productDTO);
        });

        assertEquals("Category not found with id: 1", exception.getMessage());
        verify(categoryRepository, times(1)).findById(1L);
    }

    /**
     * Test: Delete Product (Success)
     *
     * Ensures that a product is deleted successfully when it exists.
     */
    @Test
    void testDeleteProduct_Success() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        doNothing().when(productRepository).delete(any(Product.class));

        productService.deleteProduct(1L);

        verify(productRepository, times(1)).delete(product);
    }

    /**
     * Test: Delete Product (Not Found)
     *
     * Ensures that an exception is thrown when the product does not exist.
     */
    @Test
    void testDeleteProduct_NotFound() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            productService.deleteProduct(1L);
        });

        assertEquals("Product not found with id: 1", exception.getMessage());
        verify(productRepository, times(1)).findById(1L);
    }
}
