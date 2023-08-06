package com.shoes.ordering.system.domains.product.adapter.out.dataaccess.mapper;


import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.product.adapter.out.dataaccess.entity.ProductEntity;
import com.shoes.ordering.system.domains.product.domain.core.entity.Product;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductId;
import org.springframework.stereotype.Component;



@Component
public class ProductDataAccessMapper {

    public ProductEntity productToProductEntity(Product product) {
       return  ProductEntity.builder()
                .productId(product.getId().getValue())
                .name(product.getName())
                .description(product.getDescription())
               .price(product.getPrice().getAmount())
                .productCategory(product.getProductCategory())
                .build();
    }

    public Product productEntityToProduct(ProductEntity productEntity) {
        return Product.builder()
                .productId(new ProductId(productEntity.getProductId()))
                .name(productEntity.getName())
                .price(new Money(productEntity.getPrice()))
                .description(productEntity.getDescription())
                .productCategory(productEntity.getProductCategory())
                .build();
    }
}
