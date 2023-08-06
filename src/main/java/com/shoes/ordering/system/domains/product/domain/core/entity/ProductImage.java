package com.shoes.ordering.system.domains.product.domain.core.entity;

import com.shoes.ordering.system.domains.common.entity.BaseEntity;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductId;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductImageId;
import lombok.Getter;

@Getter
public class ProductImage extends BaseEntity<ProductImageId> {

    private ProductId productId;
    private final ProductImageId productImageId;
    private final String productImageUrl;

    private ProductImage(Builder builder) {
        super.setId(builder.productImageId);
        productId = builder.productId;
        productImageId = builder.productImageId;
        productImageUrl = builder.productImageUrl;
    }

    public void setProductId(ProductId productId) {
        this.productId = productId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private ProductId productId;
        private ProductImageId productImageId;
        private String productImageUrl;

        private Builder() {
        }

        public Builder productId(ProductId val) {
            productId = val;
            return this;
        }

        public Builder productImageId(ProductImageId val) {
            productImageId = val;
            return this;
        }

        public Builder productImageUrl(String val) {
            productImageUrl = val;
            return this;
        }

        public ProductImage build() {
            return new ProductImage(this);
        }
    }
}
