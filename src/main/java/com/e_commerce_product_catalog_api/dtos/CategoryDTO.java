package com.e_commerce_product_catalog_api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Represents a category in the product catalog")
public class CategoryDTO {

    @Schema(description = "The unique identifier of the category", example = "1")
    private Long id;

    @NotBlank(message = "Category name is required")
    @Schema(description = "The name of the category", example = "Electronics")
    private String name;

    @Schema(description = "A brief description of the category", example = "Devices, gadgets, and appliances")
    private String description;
}


