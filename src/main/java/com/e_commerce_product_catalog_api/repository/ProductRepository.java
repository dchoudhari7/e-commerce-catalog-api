package com.e_commerce_product_catalog_api.repository;

import com.e_commerce_product_catalog_api.entity.FilteredProducts;
import com.e_commerce_product_catalog_api.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = """
    SELECT p.id AS product_id, p.name AS product_name, p.description AS product_description, 
           p.price, p.stock, c.name AS category_name
    FROM products p 
    LEFT JOIN categories c ON p.category_id = c.id
    WHERE (:name IS NULL OR p.name ILIKE CONCAT('%', :name, '%')) 
    AND (:category IS NULL OR c.name ILIKE CONCAT('%', :category, '%')) 
    AND (:description IS NULL OR p.description ILIKE CONCAT('%', :description, '%'))
    """,
            countQuery = """
    SELECT COUNT(*) 
    FROM products p 
    LEFT JOIN categories c ON p.category_id = c.id
    WHERE (:name IS NULL OR p.name ILIKE CONCAT('%', :name, '%')) 
    AND (:category IS NULL OR c.name ILIKE CONCAT('%', :category, '%')) 
    AND (:description IS NULL OR p.description ILIKE CONCAT('%', :description, '%'))
    """, nativeQuery = true)
    Page<FilteredProducts> findByFilters(
            @Param("name") String name,
            @Param("category") String category,
            @Param("description") String description,
            Pageable pageable);
}
