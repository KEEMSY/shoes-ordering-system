package com.shoes.ordering.system.domains.product.domain.core.event;

import com.shoes.ordering.system.domains.product.domain.core.entity.Product;

import java.time.ZonedDateTime;

public class ProductUpdatedEvent extends ProductEvent{
    public ProductUpdatedEvent(Product product, ZonedDateTime createdAt) {
        super(product, createdAt);
    }
}
