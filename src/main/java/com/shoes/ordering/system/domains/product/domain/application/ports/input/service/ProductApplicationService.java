package com.shoes.ordering.system.domains.product.domain.application.ports.input.service;

import com.shoes.ordering.system.domains.product.domain.application.dto.create.CreateProductCommand;
import com.shoes.ordering.system.domains.product.domain.application.dto.create.CreateProductResponse;
import com.shoes.ordering.system.domains.product.domain.application.dto.update.UpdateProductCommand;
import com.shoes.ordering.system.domains.product.domain.application.dto.update.UpdateProductResponse;

import javax.validation.Valid;

public interface ProductApplicationService {
    CreateProductResponse createProduct(@Valid CreateProductCommand createProductCommand);
    UpdateProductResponse updateProduct(@Valid UpdateProductCommand updateProductCommand);
}
