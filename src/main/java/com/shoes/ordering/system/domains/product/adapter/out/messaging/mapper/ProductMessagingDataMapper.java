package com.shoes.ordering.system.domains.product.adapter.out.messaging.mapper;

import com.shoes.ordering.system.common.kafka.model.CreateProductRequestAvroModel;
import com.shoes.ordering.system.common.kafka.model.UpdateProductRequestAvroModel;
import com.shoes.ordering.system.common.kafka.model.ProductCategory;
import com.shoes.ordering.system.domains.product.domain.core.entity.Product;
import com.shoes.ordering.system.domains.product.domain.core.event.ProductCreatedEvent;
import com.shoes.ordering.system.domains.product.domain.core.event.ProductUpdatedEvent;
import com.shoes.ordering.system.domains.product.domain.core.exception.ProductDomainException;
import org.springframework.stereotype.Component;

@Component
public class ProductMessagingDataMapper {

    public CreateProductRequestAvroModel productCreatedEventToCreateProductRequestAvroModel(ProductCreatedEvent productCreatedEvent) {
        Product product = productCreatedEvent.getProduct();
        return CreateProductRequestAvroModel.newBuilder()
                .setProductId(product.getId().getValue().toString())
                .setName(product.getName())
                .setDescription(product.getDescription())
                .setProductCategory(domainProductCategoryToAvroProductKind(product.getProductCategory()))
                .setPrice(product.getPrice().getAmount())
                .setCreatedAt(productCreatedEvent.getCreatedAt().toInstant())
                .build();
    }

    public UpdateProductRequestAvroModel productUpdatedProductRequestAvroModel(ProductUpdatedEvent productUpdatedEvent) {
        Product product = productUpdatedEvent.getProduct();
        return UpdateProductRequestAvroModel.newBuilder()
                .setProductId(product.getId().getValue().toString())
                .setName(product.getName())
                .setDescription(product.getDescription())
                .setProductCategory(domainProductCategoryToAvroProductKind(product.getProductCategory()))
                .setPrice(product.getPrice().getAmount())
                .setCreatedAt(productUpdatedEvent.getCreatedAt().toInstant())
                .build();
    }

    private ProductCategory domainProductCategoryToAvroProductKind(com.shoes.ordering.system
                                                                           .domains.product.domain
                                                                           .core.valueobject
                                                                           .ProductCategory productCategory) {

        ProductCategory avroProductCategory;

        switch (productCategory) {
            case SHOES:
                avroProductCategory = ProductCategory.SHOES;
                break;
            case CLOTHING:
                avroProductCategory = ProductCategory.CLOTHING;
                break;
            case DISABLING:
                avroProductCategory = ProductCategory.DISABLING;
                break;
            default:
                throw new ProductDomainException("Unsupported ProductCategory: " + productCategory);
        }
        return avroProductCategory;
    }
}
