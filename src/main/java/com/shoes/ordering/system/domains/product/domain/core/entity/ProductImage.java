package com.shoes.ordering.system.domains.product.domain.core.entity;

import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductImageId;
import lombok.Getter;

@Getter
public class ProductImage {

    private final ProductImageId productImageId;
    private final String imageUrl;

    public ProductImage(ProductImageId productImageId, String imageUrl) {
        this.productImageId = productImageId;
        this.imageUrl = imageUrl;
    }
}
