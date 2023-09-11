package com.shoes.ordering.system.domains.order.adapter.out.dataaccess.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shoes.ordering.system.domains.order.adapter.out.dataaccess.entity.OrderEntity;
import com.shoes.ordering.system.domains.order.adapter.out.dataaccess.repository.OrderQuerydslRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

import static com.shoes.ordering.system.domains.order.adapter.out.dataaccess.entity.QOrderEntity.orderEntity;
import static com.shoes.ordering.system.domains.order.adapter.out.dataaccess.entity.QOrderItemEntity.orderItemEntity;

@Component
public class OrderQuerydslRepositoryImpl implements OrderQuerydslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public OrderQuerydslRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Optional<OrderEntity> findByTrackingId(UUID trackingId) {
        return Optional.ofNullable(jpaQueryFactory
                .select(orderEntity)
                .from(orderEntity)
                .leftJoin(orderEntity.items, orderItemEntity).fetchJoin()
                .where(orderEntity.trackingId.eq(trackingId))
                .fetchOne());
    }
}

