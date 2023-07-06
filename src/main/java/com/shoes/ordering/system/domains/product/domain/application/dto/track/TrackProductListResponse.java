package com.shoes.ordering.system.domains.product.domain.application.dto.track;

import com.shoes.ordering.system.domains.common.validation.SelfValidating;
import com.shoes.ordering.system.domains.product.domain.core.entity.Product;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class TrackProductListResponse extends SelfValidating<TrackProductListResponse> {

    @NotNull private final List<Product> productList;

    private TrackProductListResponse(Builder builder) {
        productList = builder.productList;
        this.validateSelf(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private @NotNull List<Product> productList;

        private Builder() {
        }

        public Builder productList(@NotNull List<Product> val) {
            productList = val;
            return this;
        }

        public TrackProductListResponse build() {
            return new TrackProductListResponse(this);
        }
    }
}
