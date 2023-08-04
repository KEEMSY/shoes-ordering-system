package com.shoes.ordering.system.domains.product.domain.application.dto.update;

import com.shoes.ordering.system.domains.common.validation.SelfValidating;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class UpdateProductCommand extends SelfValidating<UpdateProductCommand> {

    @NotNull private final UUID productId;
    @NotNull private final String name;
    @NotNull private final ProductCategory productCategory;
    @NotNull private final String description;
    @NotNull private final BigDecimal price;

    private UpdateProductCommand(Builder builder) {
        productId = builder.productId;
        name = builder.name;
        productCategory = builder.productCategory;
        description = builder.description;
        price = builder.price;

        this.validateSelf(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private @NotNull UUID productId;
        private @NotNull String name;
        private @NotNull ProductCategory productCategory;
        private @NotNull String description;
        private @NotNull BigDecimal price;

        private Builder() {
        }

        public Builder productId(@NotNull UUID val) {
            productId = val;
            return this;
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

        public UpdateProductCommand build() {
            return new UpdateProductCommand(this);
        }
    }
}
