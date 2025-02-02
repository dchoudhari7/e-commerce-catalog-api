package com.e_commerce_product_catalog_api.controller;

import com.e_commerce_product_catalog_api.dtos.OrderDTO;
import com.e_commerce_product_catalog_api.service.OrderService;
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

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@Validated
@Tag(name = "Order", description = "The Order API for managing orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    @Operation(
            summary = "Create a new Order",
            description = "Creates a new order along with the list of items included in the order",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Order created successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data provided")
            }
    )
    public ResponseEntity<OrderDTO> createOrder(
            @Parameter(description = "Order data to be created", required = true)
            @Valid @RequestBody OrderDTO orderDTO
    ) {
        OrderDTO createdOrder = orderService.createOrder(orderDTO);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(
            summary = "Get all Orders",
            description = "Retrieves all orders along with their details",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Orders retrieved successfully"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get Order by ID",
            description = "Retrieves the details of an order by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Order retrieved successfully"),
                    @ApiResponse(responseCode = "404", description = "Order not found with the provided ID")
            }
    )
    public ResponseEntity<OrderDTO> getOrderById(
            @Parameter(description = "ID of the order to be retrieved", required = true)
            @PathVariable("id") @Min(1) Long id
    ) {
        OrderDTO order = orderService.getOrderById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an Order",
            description = "Updates the details of an existing order",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Order updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data provided"),
                    @ApiResponse(responseCode = "404", description = "Order not found with the provided ID")
            }
    )
    public ResponseEntity<OrderDTO> updateOrder(
            @Parameter(description = "ID of the order to be updated", required = true)
            @PathVariable("id") @Min(1) Long id,
            @Parameter(description = "Updated order data", required = true)
            @Valid @RequestBody OrderDTO orderDTO
    ) {
        OrderDTO updatedOrder = orderService.updateOrder(id, orderDTO);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete an Order",
            description = "Deletes an order by its ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Order deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Order not found with the provided ID")
            }
    )
    public ResponseEntity<String> deleteOrder(
            @Parameter(description = "ID of the order to be deleted", required = true)
            @PathVariable("id") @Min(1) Long id
    ) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>("Order deleted successfully!", HttpStatus.OK);
    }
}
