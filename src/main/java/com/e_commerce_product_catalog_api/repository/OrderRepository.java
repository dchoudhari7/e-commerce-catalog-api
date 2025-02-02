package com.e_commerce_product_catalog_api.repository;

import com.e_commerce_product_catalog_api.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
