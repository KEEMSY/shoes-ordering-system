package com.shoes.ordering.system.domains.product.domain.core;

import com.shoes.ordering.system.domains.product.domain.core.entity.Product;
import com.shoes.ordering.system.domains.product.domain.core.event.ProductCreatedEvent;
import com.shoes.ordering.system.domains.product.domain.core.event.ProductUpdatedEvent;
import org.springframework.stereotype.Component;

@Component
public interface ProductDomainService {
     ProductCreatedEvent validateAndInitiateProduct(Product product);
     ProductUpdatedEvent validateAndUpdateProduct(Product product);
}
