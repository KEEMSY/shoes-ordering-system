package com.shoes.ordering.system.domains.product.domain.application.handler;

import com.shoes.ordering.system.domains.product.domain.application.dto.create.CreateProductCommand;
import com.shoes.ordering.system.domains.product.domain.application.dto.create.CreateProductResponse;
import com.shoes.ordering.system.domains.product.domain.application.helper.ProductHelper;
import com.shoes.ordering.system.domains.product.domain.application.mapper.ProductDataMapper;
import com.shoes.ordering.system.domains.product.domain.core.event.ProductCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class CreateProductCommandHandler {

    private final ProductHelper productHelper;
    private final ProductDataMapper productDataMapper;

    public CreateProductCommandHandler(ProductHelper productHelper,
                                       ProductDataMapper productDataMapper) {
        this.productHelper = productHelper;
        this.productDataMapper = productDataMapper;
    }
    @Transactional
    public CreateProductResponse createProduct(CreateProductCommand createProductCommand) {
        ProductCreatedEvent productCreatedEvent = productHelper.persistProduct(createProductCommand);
        log.info("Product is created with id: {}", productCreatedEvent.getProduct().getId().getValue());
        return productDataMapper.productToCreateProductResponse(
                productCreatedEvent.getProduct());
    }
}
