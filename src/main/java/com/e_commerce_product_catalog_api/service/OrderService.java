package com.e_commerce_product_catalog_api.service;

import com.e_commerce_product_catalog_api.dtos.OrderDTO;
import com.e_commerce_product_catalog_api.entity.Order;
import com.e_commerce_product_catalog_api.entity.OrderItem;
import com.e_commerce_product_catalog_api.entity.Product;
import com.e_commerce_product_catalog_api.exception.ResourceNotFoundException;
import com.e_commerce_product_catalog_api.mapper.OrderMapper;
import com.e_commerce_product_catalog_api.repository.OrderItemRepository;
import com.e_commerce_product_catalog_api.repository.OrderRepository;
import com.e_commerce_product_catalog_api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        // Step 1: Create the Order entity
        Order order = new Order();
        order.setOrderDate(orderDTO.getOrderDate());
        Order savedOrder = orderRepository.save(order);

        // Step 2: Process and create OrderItems
        List<OrderItem> orderItems = orderDTO.getOrderItems().stream().map(orderItemDTO -> {
            // Validate product
            Product product = productRepository.findById(orderItemDTO.getProduct().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + orderItemDTO.getProduct().getId()));

            // Check stock
            if (product.getStock() < orderItemDTO.getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock for product ID: " + product.getId());
            }

            // Deduct stock
            product.setStock(product.getStock() - orderItemDTO.getQuantity());
            productRepository.save(product);

            // Create OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(product);
            orderItem.setQuantity(orderItemDTO.getQuantity());
            return orderItem;
        }).collect(Collectors.toList());

        // Save all OrderItems
        orderItemRepository.saveAll(orderItems);

        // Step 3: Map and return the saved OrderDTO
        savedOrder.setOrderItems(orderItems);
        return orderMapper.toDTO(savedOrder);
    }

    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(orderMapper::toDTO).collect(Collectors.toList());
    }

    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        return orderMapper.toDTO(order);
    }

    public OrderDTO updateOrder(Long id, OrderDTO orderDTO) {
        Order existingOrder = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        existingOrder.setOrderDate(orderDTO.getOrderDate());
        Order updatedOrder = orderRepository.save(existingOrder);
        return orderMapper.toDTO(updatedOrder);
    }

    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        orderRepository.delete(order);
    }
}
