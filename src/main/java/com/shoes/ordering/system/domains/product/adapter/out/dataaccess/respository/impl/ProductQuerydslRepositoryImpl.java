package com.shoes.ordering.system.domains.product.adapter.out.dataaccess.respository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shoes.ordering.system.domains.product.adapter.out.dataaccess.entity.ProductEntity;
import com.shoes.ordering.system.domains.product.adapter.out.dataaccess.respository.ProductQuerydslRepository;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductQuerydslRepositoryImpl implements ProductQuerydslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public ProductQuerydslRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Optional<List<ProductEntity>> findByProductCategory(List<ProductCategory> productCategory) {
        return Optional.empty();
    }
}
