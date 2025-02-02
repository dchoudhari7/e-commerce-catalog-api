package com.e_commerce_product_catalog_api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Represents an item in an order, containing the product and its quantity")
public class OrderItemDTO {

    @Schema(description = "The unique identifier of the order item", example = "1")
    private Long id;

    @NotNull(message = "Product is required")
    @Schema(description = "The product associated with this order item")
    private ProductDTO product;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Schema(description = "The quantity of the product in the order", example = "2")
    private Integer quantity;
}

