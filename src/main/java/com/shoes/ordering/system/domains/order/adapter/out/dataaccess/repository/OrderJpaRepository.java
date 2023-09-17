package com.shoes.ordering.system.domains.order.adapter.out.dataaccess.repository;

import com.shoes.ordering.system.domains.order.adapter.out.dataaccess.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity, UUID> {

    @Query("SELECT o FROM OrderEntity o JOIN FETCH o.items WHERE o.trackingId = :trackingId")
    Optional<OrderEntity> findByTrackingId(@Param("trackingId") UUID trackingId);
}
