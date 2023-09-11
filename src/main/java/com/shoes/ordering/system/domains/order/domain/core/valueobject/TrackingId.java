package com.shoes.ordering.system.domains.order.domain.core.valueobject;

import com.shoes.ordering.system.domains.common.valueobject.BaseId;

import java.util.UUID;

public class TrackingId extends BaseId<UUID> {

    public TrackingId(UUID value) {
        super(value);
    }
}
