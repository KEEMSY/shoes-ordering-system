package com.shoes.ordering.system.domains.product.domain.application.dto.create;

import com.shoes.ordering.system.domains.common.validation.SelfValidating;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@AllArgsConstructor
public class CreateProductCommand extends SelfValidating<CreateProductCommand> {

    @NotNull private final String name;
    @NotNull private final ProductCategory productCategory;
    @NotNull private final String description;
    @NotNull private final BigDecimal price;

    private CreateProductCommand(Builder builder) {
        name = builder.name;
        productCategory = builder.productCategory;
        description = builder.description;
        price = builder.price;

        this.validateSelf(this);
    }

    public String getName() {
        return name;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private @NotNull String name;
        private @NotNull ProductCategory productCategory;
        private @NotNull String description;
        private @NotNull BigDecimal price;

        private Builder() {
        }

        public Builder name(@NotNull String val) {
            name = val;
            return this;
        }

        public Builder productCategory(@NotNull ProductCategory val) {
            productCategory = val;
            return this;
        }

        public Builder description(@NotNull String val) {
            description = val;
            return this;
        }

        public Builder price(@NotNull BigDecimal val) {
            price = val;
            return this;
        }

        public CreateProductCommand build() {
            return new CreateProductCommand(this);
        }
    }
}
