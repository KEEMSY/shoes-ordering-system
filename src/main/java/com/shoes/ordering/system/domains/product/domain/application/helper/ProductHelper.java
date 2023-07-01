package com.shoes.ordering.system.domains.product.domain.application.helper;

import com.shoes.ordering.system.domains.product.domain.application.dto.create.CreateProductCommand;
import com.shoes.ordering.system.domains.product.domain.application.mapper.ProductDataMapper;
import com.shoes.ordering.system.domains.product.domain.application.ports.output.repository.ProductRepository;
import com.shoes.ordering.system.domains.product.domain.core.ProductDomainService;
import com.shoes.ordering.system.domains.product.domain.core.entity.Product;
import com.shoes.ordering.system.domains.product.domain.core.event.ProductCreatedEvent;
import com.shoes.ordering.system.domains.product.domain.core.exception.ProductDomainException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductHelper {

    private final ProductDomainService productDomainService;
    private final ProductRepository productRepository;
    private final ProductDataMapper productDataMapper;

    public ProductHelper(ProductDomainService productDomainService,
                         ProductRepository productRepository,
                         ProductDataMapper productDataMapper) {
        this.productDomainService = productDomainService;
        this.productRepository = productRepository;
        this.productDataMapper = productDataMapper;
    }

    public ProductCreatedEvent persistProduct(CreateProductCommand createProductCommand) {
        Product product = productDataMapper.creatProductCommandToProduct(createProductCommand);
        ProductCreatedEvent productCreatedEvent = productDomainService.validateAndInitiateProduct(product);
        saveProduct(product);

        return productCreatedEvent;
    }

    private Product saveProduct(Product product) {
        Product productResult = productRepository.save(product);
        if (productResult == null) {
            log.error("Could not save product!");
            throw new ProductDomainException("Could not save product");
        }

        log.info("Product is saved with id: {}", product.getId().getValue());
        return productResult;
    }
}
