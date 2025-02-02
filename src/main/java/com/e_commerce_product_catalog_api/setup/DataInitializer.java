package com.e_commerce_product_catalog_api.setup;

import com.e_commerce_product_catalog_api.entity.Category;
import com.e_commerce_product_catalog_api.entity.Order;
import com.e_commerce_product_catalog_api.entity.OrderItem;
import com.e_commerce_product_catalog_api.entity.Product;
import com.e_commerce_product_catalog_api.repository.CategoryRepository;
import com.e_commerce_product_catalog_api.repository.OrderItemRepository;
import com.e_commerce_product_catalog_api.repository.OrderRepository;
import com.e_commerce_product_catalog_api.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class DataInitializer {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public DataInitializer(CategoryRepository categoryRepository, ProductRepository productRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @PostConstruct
    public void InitializeData() {
        // Add categories
        Category electronics = new Category();
        electronics.setName("Electronics");
        electronics.setDescription("Devices, gadgets, and appliances.");
        categoryRepository.save(electronics);

        Category clothing = new Category();
        clothing.setName("Clothing");
        clothing.setDescription("Apparel and garments.");
        categoryRepository.save(clothing);

        Category books = new Category();
        books.setName("Books");
        books.setDescription("Fiction and non-fiction books.");
        categoryRepository.save(books);

        // Add products
        Product phone = new Product();
        phone.setName("Phone");
        phone.setDescription("Smartphone with 5G connectivity.");
        phone.setPrice(new BigDecimal("500.00"));
        phone.setStock(50);
        phone.setCategory(electronics);
        productRepository.save(phone);

        Product laptop = new Product();
        laptop.setName("Laptop");
        laptop.setDescription("High-performance laptop for work and gaming.");
        laptop.setPrice(new BigDecimal("1000.00"));
        laptop.setStock(30);
        laptop.setCategory(electronics);
        productRepository.save(laptop);

        Product tshirt = new Product();
        tshirt.setName("T-Shirt");
        tshirt.setDescription("Cotton T-shirt in various sizes.");
        tshirt.setPrice(new BigDecimal("20.00"));
        tshirt.setStock(100);
        tshirt.setCategory(clothing);
        productRepository.save(tshirt);

        Product novel = new Product();
        novel.setName("Novel");
        novel.setDescription("Bestselling fiction novel.");
        novel.setPrice(new BigDecimal("15.00"));
        novel.setStock(200);
        novel.setCategory(books);
        productRepository.save(novel);

        // Add orders
        Order order1 = new Order();
        order1.setOrderDate(LocalDateTime.now().minusDays(2));
        orderRepository.save(order1);

        Order order2 = new Order();
        order2.setOrderDate(LocalDateTime.now().minusDays(1));
        orderRepository.save(order2);

        // Add order items
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setOrder(order1);
        orderItem1.setProduct(phone);
        orderItem1.setQuantity(2);
        orderItemRepository.save(orderItem1);

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setOrder(order1);
        orderItem2.setProduct(novel);
        orderItem2.setQuantity(1);
        orderItemRepository.save(orderItem2);

        OrderItem orderItem3 = new OrderItem();
        orderItem3.setOrder(order2);
        orderItem3.setProduct(laptop);
        orderItem3.setQuantity(1);
        orderItemRepository.save(orderItem3);

        OrderItem orderItem4 = new OrderItem();
        orderItem4.setOrder(order2);
        orderItem4.setProduct(tshirt);
        orderItem4.setQuantity(3);
        orderItemRepository.save(orderItem4);

        System.out.println("Initial data added successfully!");
    }
}
