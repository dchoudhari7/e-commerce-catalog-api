package com.e_commerce_product_catalog_api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "Represents an order containing order items and the order date")
public class OrderDTO {

    @Schema(description = "The unique identifier of the order", example = "1")
    private Long id;

    @NotNull(message = "Order date is required")
    @Schema(description = "The date and time when the order was placed", example = "2025-01-24T10:00:00")
    private LocalDateTime orderDate;

    @Valid
    @NotNull(message = "Order items are required")
    @Schema(description = "The list of items included in the order")
    private List<OrderItemDTO> orderItems;
}

