package com.shoes.ordering.system.domains.payment.domain.core.valueobject;

import com.shoes.ordering.system.domains.common.valueobject.BaseId;

import java.util.UUID;

public class PaymentId extends BaseId<UUID> {
    public PaymentId(UUID value) {
        super(value);
    }
}
