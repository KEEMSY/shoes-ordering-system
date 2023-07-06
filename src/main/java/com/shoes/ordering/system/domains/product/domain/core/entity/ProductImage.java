package com.shoes.ordering.system.domains.product.domain.core.entity;

import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductImageId;
import lombok.Getter;

@Getter
public class ProductImage {

    private final ProductImageId productImageId;
    private final String productImageUrl;

    public ProductImage(ProductImageId productImageId, String productImageUrl) {
        this.productImageId = productImageId;
        this.productImageUrl = productImageUrl;
    }
}
