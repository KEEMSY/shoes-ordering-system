package com.shoes.ordering.system.domains.product.domain.application.handler;

import com.shoes.ordering.system.domains.product.domain.application.dto.update.UpdateProductCommand;
import com.shoes.ordering.system.domains.product.domain.application.dto.update.UpdateProductResponse;
import com.shoes.ordering.system.domains.product.domain.application.helper.ProductHelper;
import com.shoes.ordering.system.domains.product.domain.application.mapper.ProductDataMapper;
import com.shoes.ordering.system.domains.product.domain.application.ports.output.message.publisher.ProductUpdatedRequestMessagePublisher;
import com.shoes.ordering.system.domains.product.domain.core.event.ProductUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UpdateProductCommandHandler {

    private final ProductHelper productHelper;
    private final ProductDataMapper productDataMapper;
    private final ProductUpdatedRequestMessagePublisher productUpdatedRequestMessagePublisher;

    public UpdateProductCommandHandler(ProductHelper productHelper,
                                       ProductDataMapper productDataMapper,
                                       ProductUpdatedRequestMessagePublisher productUpdatedRequestMessagePublisher) {
        this.productHelper = productHelper;
        this.productDataMapper = productDataMapper;
        this.productUpdatedRequestMessagePublisher = productUpdatedRequestMessagePublisher;
    }

    public UpdateProductResponse updateProduct(UpdateProductCommand updateProductCommand) {
        ProductUpdatedEvent productUpdatedEvent = productHelper.updateProductPersist(updateProductCommand);
        log.info("Product is updated with id: {}", productUpdatedEvent.getProduct().getId().getValue());
        productUpdatedRequestMessagePublisher.publish(productUpdatedEvent);
        return productDataMapper.productToUpdateProductResponse(productUpdatedEvent.getProduct());
    }
}
