package com.shoes.ordering.system.domains.product.adapter.out.dataaccess.adapter;

import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ProductSearchPersistenceRequest {
     private final String name;
    @NotNull private final List<ProductCategory> productCategoryList;
    @NotNull private final Money minPrice;
    @NotNull private final Money maxPrice;

    private ProductSearchPersistenceRequest(Builder builder) {
        name = builder.name;
        productCategoryList = builder.productCategoryList;
        minPrice = builder.minPrice;
        maxPrice = builder.maxPrice;
    }
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String name;
        private @NotNull List<ProductCategory> productCategoryList;
        private @NotNull Money minPrice;
        private @NotNull Money maxPrice;

        private Builder() {
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder productCategoryList(@NotNull List<ProductCategory> val) {
            productCategoryList = val;
            return this;
        }

        public Builder minPrice(@NotNull Money val) {
            minPrice = val;
            return this;
        }

        public Builder maxPrice(@NotNull Money val) {
            maxPrice = val;
            return this;
        }

        public ProductSearchPersistenceRequest build() {
            return new ProductSearchPersistenceRequest(this);
        }
    }
}
