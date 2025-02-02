package com.e_commerce_product_catalog_api.service;

import com.e_commerce_product_catalog_api.dtos.OrderDTO;
import com.e_commerce_product_catalog_api.dtos.OrderItemDTO;
import com.e_commerce_product_catalog_api.dtos.ProductDTO;
import com.e_commerce_product_catalog_api.entity.Order;
import com.e_commerce_product_catalog_api.entity.OrderItem;
import com.e_commerce_product_catalog_api.entity.Product;
import com.e_commerce_product_catalog_api.exception.ResourceNotFoundException;
import com.e_commerce_product_catalog_api.mapper.OrderMapper;
import com.e_commerce_product_catalog_api.repository.OrderItemRepository;
import com.e_commerce_product_catalog_api.repository.OrderRepository;
import com.e_commerce_product_catalog_api.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderService orderService;

    private Order order;
    private OrderDTO orderDTO;
    private OrderItem orderItem;
    private OrderItemDTO orderItemDTO;
    private Product product;
    private ProductDTO productDTO;

    /**
     * Sets up test data before each test method.
     */
    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setStock(10);

        productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Laptop");
        productDTO.setStock(10);

        order = new Order();
        order.setId(1L);
        order.setOrderDate(LocalDateTime.now());

        orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(2);

        order.setOrderItems(List.of(orderItem));

        orderItemDTO = new OrderItemDTO();
        orderItemDTO.setId(1L);
        orderItemDTO.setProduct(productDTO);
        orderItemDTO.setQuantity(2);

        orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        orderDTO.setOrderDate(LocalDateTime.now());
        orderDTO.setOrderItems(List.of(orderItemDTO));
    }

    /**
     * Test: Create Order
     *
     * Ensures that an order is created successfully and its order items are processed correctly.
     */
    @Test
    void testCreateOrder() {
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(orderItemRepository.saveAll(any())).thenReturn(List.of(orderItem));
        when(orderMapper.toDTO(any(Order.class))).thenReturn(orderDTO);

        OrderDTO result = orderService.createOrder(orderDTO);

        assertNotNull(result);
        assertEquals(orderDTO.getId(), result.getId());
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderItemRepository, times(1)).saveAll(any());
    }

    /**
     * Test: Get All Orders
     *
     * Ensures that all orders are retrieved successfully.
     */
    @Test
    void testGetAllOrders() {
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order));
        when(orderMapper.toDTO(any(Order.class))).thenReturn(orderDTO);

        List<OrderDTO> result = orderService.getAllOrders();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(orderRepository, times(1)).findAll();
    }

    /**
     * Test: Get Order by ID (Found)
     *
     * Ensures that an order is returned when the ID exists.
     */
    @Test
    void testGetOrderById_Found() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        when(orderMapper.toDTO(any(Order.class))).thenReturn(orderDTO);

        OrderDTO result = orderService.getOrderById(1L);

        assertNotNull(result);
        assertEquals(orderDTO.getId(), result.getId());
        verify(orderRepository, times(1)).findById(1L);
    }

    /**
     * Test: Get Order by ID (Not Found)
     *
     * Ensures that an exception is thrown when the order does not exist.
     */
    @Test
    void testGetOrderById_NotFound() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            orderService.getOrderById(1L);
        });

        assertEquals("Order not found with id: 1", exception.getMessage());
        verify(orderRepository, times(1)).findById(1L);
    }

    /**
     * Test: Update Order (Success)
     *
     * Ensures that an order is updated successfully when it exists.
     */
    @Test
    void testUpdateOrder_Success() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderMapper.toDTO(any(Order.class))).thenReturn(orderDTO);

        OrderDTO result = orderService.updateOrder(1L, orderDTO);

        assertNotNull(result);
        assertEquals(orderDTO.getId(), result.getId());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    /**
     * Test: Update Order (Not Found)
     *
     * Ensures that an exception is thrown when the order does not exist.
     */
    @Test
    void testUpdateOrder_NotFound() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            orderService.updateOrder(1L, orderDTO);
        });

        assertEquals("Order not found with id: 1", exception.getMessage());
        verify(orderRepository, times(1)).findById(1L);
    }

    /**
     * Test: Delete Order (Success)
     *
     * Ensures that an order is deleted successfully when it exists.
     */
    @Test
    void testDeleteOrder_Success() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        doNothing().when(orderRepository).delete(any(Order.class));

        orderService.deleteOrder(1L);

        verify(orderRepository, times(1)).delete(order);
    }

    /**
     * Test: Delete Order (Not Found)
     *
     * Ensures that an exception is thrown when the order does not exist.
     */
    @Test
    void testDeleteOrder_NotFound() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            orderService.deleteOrder(1L);
        });

        assertEquals("Order not found with id: 1", exception.getMessage());
        verify(orderRepository, times(1)).findById(1L);
    }
}
