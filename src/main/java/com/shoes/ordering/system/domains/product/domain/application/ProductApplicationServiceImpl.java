package com.shoes.ordering.system.domains.product.domain.application;

import com.shoes.ordering.system.domains.product.domain.application.dto.create.CreateProductCommand;
import com.shoes.ordering.system.domains.product.domain.application.dto.create.CreateProductResponse;
import com.shoes.ordering.system.domains.product.domain.application.dto.update.UpdateProductCommand;
import com.shoes.ordering.system.domains.product.domain.application.dto.update.UpdateProductResponse;
import com.shoes.ordering.system.domains.product.domain.application.handler.CreateProductCommandHandler;
import com.shoes.ordering.system.domains.product.domain.application.handler.UpdateProductCommandHandler;
import com.shoes.ordering.system.domains.product.domain.application.ports.input.service.ProductApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
public class ProductApplicationServiceImpl implements ProductApplicationService {

    private final CreateProductCommandHandler createProductCommandHandler;
    private final UpdateProductCommandHandler updateProductCommandHandler;
    public ProductApplicationServiceImpl(CreateProductCommandHandler createProductCommandHandler,
                                         UpdateProductCommandHandler updateProductCommandHandler) {
        this.createProductCommandHandler = createProductCommandHandler;
        this.updateProductCommandHandler = updateProductCommandHandler;
    }

    @Override
    public CreateProductResponse createProduct(CreateProductCommand createProductCommand) {
        return createProductCommandHandler.createProduct(createProductCommand);
    }

    @Override
    public UpdateProductResponse updateProduct(UpdateProductCommand updateProductCommand) {
        return updateProductCommandHandler.updateProduct(updateProductCommand);
    }
}
