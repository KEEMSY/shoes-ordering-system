package com.shoes.ordering.system.domains.product.domain.application.ports.output.repository;

import com.shoes.ordering.system.domains.product.domain.application.dto.track.DynamicSearchProductQuery;
import com.shoes.ordering.system.domains.product.domain.core.entity.Product;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findByProductId(UUID productId);
    Optional<List<Product>> findByProductCategory(List<ProductCategory> productCategory);
    Optional<List<Product>> searchProductsByDynamicQuery(DynamicSearchProductQuery query);
}
