package com.shoes.ordering.system.domains.product.domain.application.dto.create;

import com.shoes.ordering.system.domains.common.validation.SelfValidating;
import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
public class CreateProductResponse extends SelfValidating<CreateProductResponse> {

    @NotNull private final UUID productId;
    @NotNull private final String name;
    @NotNull private final ProductCategory productCategory;
    @NotNull private final String description;
    @NotNull private final Money price;

    private CreateProductResponse(CreateProductResponse.Builder builder) {
        productId = builder.productId;
        name = builder.name;
        productCategory = builder.productCategory;
        description = builder.description;
        price = builder.price;

        this.validateSelf(this);
    }

    public static CreateProductResponse.Builder builder() {
        return new CreateProductResponse.Builder();
    }

    public static final class Builder {
        private @NotNull UUID productId;
        private @NotNull String name;
        private @NotNull ProductCategory productCategory;
        private @NotNull String description;
        private @NotNull Money price;

        private Builder() {
        }

        public CreateProductResponse.Builder productId(@NotNull UUID val) {
            productId = val;
            return this;
        }
        public CreateProductResponse.Builder name(@NotNull String val) {
            name = val;
            return this;
        }

        public CreateProductResponse.Builder productCategory(@NotNull ProductCategory val) {
            productCategory = val;
            return this;
        }

        public CreateProductResponse.Builder description(@NotNull String val) {
            description = val;
            return this;
        }

        public CreateProductResponse.Builder price(@NotNull Money val) {
            price = val;
            return this;
        }

        public CreateProductResponse build() {
            return new CreateProductResponse(this);
        }
    }
}
