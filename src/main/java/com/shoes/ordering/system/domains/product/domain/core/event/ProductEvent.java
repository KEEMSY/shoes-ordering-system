package com.shoes.ordering.system.domains.product.domain.core.event;

import com.shoes.ordering.system.domains.common.event.DomainEvent;
import com.shoes.ordering.system.domains.product.domain.core.entity.Product;

import java.time.ZonedDateTime;

public abstract class ProductEvent implements DomainEvent<Product> {
    private final Product product;
    private final ZonedDateTime createdAt;

    protected ProductEvent(Product product, ZonedDateTime createdAt) {
        this.product = product;
        this.createdAt = createdAt;
    }

    public Product getProduct() {
        return product;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}

