package com.shoes.ordering.system.domains.product.domain.application.dto.track;

import com.shoes.ordering.system.domains.common.validation.SelfValidating;
import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Getter
public class TrackProductResponse extends SelfValidating<TrackProductResponse> {

    @NotNull private final UUID productId;
    @NotNull private final String name;
    @NotNull private final ProductCategory productCategory;
    @NotNull private final String description;
    @NotNull private final Money price;

    public static Builder builder() {
        return new Builder();
    }

    private TrackProductResponse(Builder builder) {
        productId = builder.productId;
        name = builder.name;
        productCategory = builder.productCategory;
        description = builder.description;
        price = builder.price;

        this.validateSelf(this);
    }

    public static final class Builder {
        private @NotNull UUID productId;
        private @NotNull String name;
        private @NotNull ProductCategory productCategory;
        private @NotNull String description;
        private @NotNull Money price;

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

        public Builder price(@NotNull Money val) {
            price = val;
            return this;
        }

        public TrackProductResponse build() {
            return new TrackProductResponse(this);
        }
    }
}
