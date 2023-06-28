package com.shoes.ordering.system.domains.product.domain.core.event;

import com.shoes.ordering.system.domains.product.domain.core.entity.Product;

import java.time.ZonedDateTime;

public class ProductCreatedEvent extends ProductEvent{
    public ProductCreatedEvent(Product product, ZonedDateTime createdAt) {
        super(product, createdAt);
    }
}
