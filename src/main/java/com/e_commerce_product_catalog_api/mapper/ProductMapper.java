package com.e_commerce_product_catalog_api.mapper;

import com.e_commerce_product_catalog_api.dtos.ProductDTO;
import com.e_commerce_product_catalog_api.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper (componentModel = "spring", uses = {CategoryMapper.class})
@Component
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "category.id", target = "categoryId")
    ProductDTO toDTO(Product product);

    @Mapping(source = "categoryId", target = "category", qualifiedByName = "categoryFromId") // Convert categoryId to a Category object
    Product toEntity(ProductDTO productDTO);
}

