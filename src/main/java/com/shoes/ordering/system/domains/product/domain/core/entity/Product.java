package com.shoes.ordering.system.domains.product.domain.core.entity;

import com.shoes.ordering.system.domains.common.entity.AggregateRoot;
import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.product.domain.core.exception.ProductDomainException;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductId;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Product extends AggregateRoot<ProductId> {

    private final String name;
    private final ProductCategory productCategory;
    private final String description;
    private final Money price;
    private final List<ProductImage> productImages;

    public static Builder builder() {
        return new Builder();
    }

    public Product(String name,
                   ProductCategory productCategory,
                   String description,
                   Money price,
                   List<ProductImage> productImages) {
        this.name = name;
        this.productCategory = productCategory;
        this.description = description;
        this.price = price;
        this.productImages = productImages;
    }

    public void initializeProduct() {
        setId(new ProductId(UUID.randomUUID()));
    }

    public void validateProduct() {
        validateProductCategory();
        validatePrice();
    }

    private void validatePrice() {
        if (price == null || !price.isGreaterThanZero()) {
            throw new ProductDomainException("Price must be greater than zero!");
        }
    }

    private void validateProductCategory() {
        if (productCategory.equals(ProductCategory.DISABLING) || getId() ==null) {
            throw new ProductDomainException("Product is not a valid category for creating the product");
        }
    }

    private Product(Builder builder) {
        super.setId(builder.productId);
        name = builder.name;
        productCategory = builder.productCategory;
        description = builder.description;
        price = builder.price;
        productImages = builder.productImages;
    }


    public static final class Builder {
        private ProductId productId;
        private String name;
        private ProductCategory productCategory;
        private String description;
        private Money price;
        private List<ProductImage> productImages;

        private Builder() {
        }

        public Builder productId(ProductId val) {
            productId = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder productCategory(ProductCategory val) {
            productCategory = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder productImages(List<ProductImage> val) {
            productImages = val;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
