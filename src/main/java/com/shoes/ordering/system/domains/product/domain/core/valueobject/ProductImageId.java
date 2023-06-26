package com.shoes.ordering.system.domains.product.domain.core.valueobject;

import com.shoes.ordering.system.domains.common.valueobject.BaseId;

import java.util.UUID;

public class ProductImageId extends BaseId<UUID> {
    protected ProductImageId(UUID value) {
        super(value);
    }
}
