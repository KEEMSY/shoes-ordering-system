package com.shoes.ordering.system.domains.product.adapter.out.dataaccess.respository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shoes.ordering.system.domains.product.adapter.out.dataaccess.respository.ProductQuerydslRepository;
import com.shoes.ordering.system.domains.product.domain.core.entity.Product;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;

import java.util.List;
import java.util.Optional;

public class ProductQuerydslRepositoryImpl implements ProductQuerydslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public ProductQuerydslRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Optional<List<Product>> findByProductCategory(List<ProductCategory> productCategory) {
        return Optional.empty();
    }
}
