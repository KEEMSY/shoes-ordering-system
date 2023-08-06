package com.shoes.ordering.system.domains.product.domain.application.dto.track;

import com.shoes.ordering.system.domains.common.validation.SelfValidating;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
public class TrackProductQuery extends SelfValidating<TrackProductQuery> {

    @NotNull private final UUID productId;

    private TrackProductQuery(Builder builder) {
        productId = builder.productId;

        this.validateSelf(this);
    }
    public static Builder builder() {
        return new Builder();
    }
    public static final class Builder {
        private @NotNull UUID productId;

        private Builder() {
        }
        public Builder productId(@NotNull UUID val) {
            productId = val;
            return this;
        }
        public TrackProductQuery build() {
            return new TrackProductQuery(this);
        }
    }
}
