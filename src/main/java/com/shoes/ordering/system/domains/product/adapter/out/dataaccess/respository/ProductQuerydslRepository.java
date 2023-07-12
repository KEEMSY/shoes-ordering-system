package com.shoes.ordering.system.domains.product.adapter.out.dataaccess.respository;

import com.shoes.ordering.system.domains.product.adapter.out.dataaccess.entity.ProductEntity;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductQuerydslRepository {
    Optional<List<ProductEntity>> findByProductCategory(List<ProductCategory> productCategory);
}
