package com.shoes.ordering.system.domains.order.domain.application.dto.track;

import com.shoes.ordering.system.domains.common.validation.SelfValidating;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class TrackOrderQuery extends SelfValidating<TrackOrderQuery> {
    @NotNull
    private final UUID orderTrackingId;

    private TrackOrderQuery(Builder builder) {
        orderTrackingId = builder.orderTrackingId;

        validateSelf(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private @NotNull UUID orderTrackingId;

        private Builder() {
        }

        public Builder orderTrackingId(@NotNull UUID val) {
            orderTrackingId = val;
            return this;
        }

        public TrackOrderQuery build() {
            return new TrackOrderQuery(this);
        }
    }
}
