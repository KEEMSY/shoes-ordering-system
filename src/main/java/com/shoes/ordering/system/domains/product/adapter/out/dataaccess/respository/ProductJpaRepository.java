package com.shoes.ordering.system.domains.product.adapter.out.dataaccess.respository;

import com.shoes.ordering.system.domains.product.adapter.out.dataaccess.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity, UUID> {
    Optional<ProductEntity> findByProductId(UUID productId);
}
