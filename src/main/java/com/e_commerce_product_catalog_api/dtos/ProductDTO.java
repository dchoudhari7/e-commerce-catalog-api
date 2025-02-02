package com.e_commerce_product_catalog_api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Represents a product in the catalog")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {

    @Schema(description = "The unique identifier of the product", example = "1")
    private Long id;

    @NotBlank(message = "Product name is required")
    @Schema(description = "The name of the product", example = "Smartphone")
    private String name;

    @Schema(description = "The description of the product", example = "A high-end smartphone with 5G support")
    private String description;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    @Schema(description = "The price of the product", example = "599.99")
    private BigDecimal price;

    @NotNull(message = "Stock is required")
    @Min(value = 0, message = "Stock must be greater than or equal to 0")
    @Schema(description = "The available stock for the product", example = "100")
    private Integer stock;

    @NotNull(message = "Category ID is required")
    @Min(value = 1, message = "Category ID must be valid")
    @Schema(description = "The unique identifier of the category to which the product belongs", example = "1")
    private Long categoryId;

    @Schema(description = "The name of the category the product belongs to", example = "Electronics")
    private String categoryName;
}
