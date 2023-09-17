package com.shoes.ordering.system.domains.product.adapter.out.dataaccess.respository.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.product.adapter.out.dataaccess.adapter.ProductSearchPersistenceRequest;
import com.shoes.ordering.system.domains.product.adapter.out.dataaccess.entity.ProductEntity;
import com.shoes.ordering.system.domains.product.adapter.out.dataaccess.respository.ProductQuerydslRepository;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.shoes.ordering.system.domains.product.adapter.out.dataaccess.entity.QProductEntity.productEntity;

@Component
public class ProductQuerydslRepositoryImpl implements ProductQuerydslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public ProductQuerydslRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Optional<List<ProductEntity>> findByProductCategory(List<ProductCategory> productCategory) {
        List<ProductEntity> result = jpaQueryFactory
                .select(productEntity)
                .from(productEntity)
                .where(productCategoryIn(productCategory))
                .fetch();
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<List<ProductEntity>> searchProductsByDynamicQuery(ProductSearchPersistenceRequest productSearchPersistenceRequest) {
        List<ProductEntity> result = jpaQueryFactory
                .select(productEntity)
                .from(productEntity)
                .where(
                        nameContains(productSearchPersistenceRequest.getName()),
                        priceBetween(productSearchPersistenceRequest.getMinPrice(), productSearchPersistenceRequest.getMaxPrice()),
                        productCategoryIn(productSearchPersistenceRequest.getProductCategoryList())
                )
                .fetch();
        return Optional.of(result);
    }

    private BooleanExpression nameContains(String name) {
        return name != null ? productEntity.name.contains(name) : null;
    }

    private BooleanExpression priceBetween(Money minPrice, Money maxPrice) {
        return productEntity.price.between(
                minPrice.getAmount(),
                maxPrice.getAmount()
        );
    }

    private BooleanExpression productCategoryIn(List<ProductCategory> productCategoryList) {
        return productEntity.productCategory.in(productCategoryList);
    }


}
