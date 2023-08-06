package com.shoes.ordering.system.domains.product.domain.core.valueobject;

import com.shoes.ordering.system.domains.common.valueobject.BaseId;

import java.util.UUID;

public class ProductId extends BaseId<UUID> {

    public ProductId(UUID value) {
        super(value);
    }
}
