package com.shoes.ordering.system.domains.product.domain.core;

import com.shoes.ordering.system.domains.product.domain.core.entity.Product;
import com.shoes.ordering.system.domains.product.domain.core.event.ProductCreatedEvent;
import com.shoes.ordering.system.domains.product.domain.core.event.ProductUpdatedEvent;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
public class ProductDomainServiceImpl implements ProductDomainService{
    private static final String UTC = "UTC";
    @Override
    public ProductCreatedEvent validateAndInitiateProduct(Product product) {
        product.initializeProduct();
        product.validateProduct();
        log.info("Product with id: {} is created", product.getId().getValue());
        return new ProductCreatedEvent(product, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public ProductUpdatedEvent validateAndUpdateProduct(Product product) {
        product.validateUpdateProduct();
        log.info("Product with Id: {} is changed", product.getId().getValue());
        return new ProductUpdatedEvent(product, ZonedDateTime.now(ZoneId.of(UTC)));
    }
}
