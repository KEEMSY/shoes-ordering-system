package com.shoes.ordering.system.domains.order.domain.application.dto.create;

import com.shoes.ordering.system.domains.common.validation.SelfValidating;
import com.shoes.ordering.system.domains.order.domain.core.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class CreateOrderCommand extends SelfValidating<CreateOrderCommand> {
    @NotNull
    private final UUID memberId;
    @NotNull
    private final BigDecimal price;
    @NotNull
    private final List<OrderItem> items;
    @NotNull
    private final OrderAddress address;

    private CreateOrderCommand(Builder builder) {
        memberId = builder.memberId;
        price = builder.price;
        items = builder.items;
        address = builder.address;

        validateSelf(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private @NotNull UUID memberId;
        private @NotNull BigDecimal price;
        private @NotNull List<OrderItem> items;
        private @NotNull OrderAddress address;

        private Builder() {
        }


        public Builder memberId(@NotNull UUID val) {
            memberId = val;
            return this;
        }

        public Builder price(@NotNull BigDecimal val) {
            price = val;
            return this;
        }

        public Builder items(@NotNull List<OrderItem> val) {
            items = val;
            return this;
        }

        public Builder address(@NotNull OrderAddress val) {
            address = val;
            return this;
        }

        public CreateOrderCommand build() {
            return new CreateOrderCommand(this);
        }
    }
}

