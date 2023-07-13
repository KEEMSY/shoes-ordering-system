package com.shoes.ordering.system.domains.product.adapter.out.dataaccess.adapter;

import com.shoes.ordering.system.domains.product.adapter.out.dataaccess.entity.ProductEntity;
import com.shoes.ordering.system.domains.product.adapter.out.dataaccess.mapper.ProductDataAccessMapper;
import com.shoes.ordering.system.domains.product.adapter.out.dataaccess.respository.ProductJpaRepository;
import com.shoes.ordering.system.domains.product.adapter.out.dataaccess.respository.ProductQuerydslRepository;
import com.shoes.ordering.system.domains.product.domain.application.ports.output.repository.ProductRepository;
import com.shoes.ordering.system.domains.product.domain.core.entity.Product;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ProductPersistenceAdapter implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;
    private final ProductQuerydslRepository productQuerydslRepository;
    private final ProductDataAccessMapper productDataAccessMapper;

    public ProductPersistenceAdapter(ProductJpaRepository productJpaRepository,
                                     ProductQuerydslRepository productQuerydslRepository,
                                     ProductDataAccessMapper productDataAccessMapper) {
        this.productJpaRepository = productJpaRepository;
        this.productQuerydslRepository = productQuerydslRepository;
        this.productDataAccessMapper = productDataAccessMapper;
    }

    @Override
    public Product save(Product product) {
        return productDataAccessMapper.productEntityToProduct(productJpaRepository
                .save(productDataAccessMapper.productToProductEntity(product)));
    }

    @Override
    public Optional<Product> findByProductId(UUID productId) {
        return productJpaRepository.findByProductId(productId)
                .map(productDataAccessMapper::productEntityToProduct);
    }

    @Override
    public Optional<List<Product>> findByProductCategory(List<ProductCategory> productCategory) {
        Optional<List<ProductEntity>> productEntities = productQuerydslRepository.findByProductCategory(productCategory);

        return productEntities.map(productEntity ->
                productEntity.stream()
                        .map(productDataAccessMapper::productEntityToProduct)
                        .collect(Collectors.toList()));

    }
}
