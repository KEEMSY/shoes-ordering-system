package com.shoes.ordering.system.domains.order.domain.core.valueobject;

import com.shoes.ordering.system.domains.common.valueobject.BaseId;

public class OrderItemId extends BaseId<Long> {
    public OrderItemId(Long value) {
        super(value);
    }
}
