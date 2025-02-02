package com.e_commerce_product_catalog_api.entity;

import java.math.BigDecimal;

public interface FilteredProducts {

    Long getProductId();
    String getProductName();
    String getProductDescription();
    BigDecimal getPrice();
    Integer getStock();
    String getCategoryName();

}
