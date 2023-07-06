package com.shoes.ordering.system.domains.product.domain.application.handler;

import com.shoes.ordering.system.domains.product.domain.application.dto.track.TrackProductListQuery;
import com.shoes.ordering.system.domains.product.domain.application.dto.track.TrackProductListResponse;
import com.shoes.ordering.system.domains.product.domain.application.mapper.ProductDataMapper;
import com.shoes.ordering.system.domains.product.domain.application.ports.output.repository.ProductRepository;
import com.shoes.ordering.system.domains.product.domain.core.entity.Product;
import com.shoes.ordering.system.domains.product.domain.core.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class TrackProductListQueryHandler {

    private final ProductDataMapper productDataMapper;
    private final ProductRepository productRepository;

    public TrackProductListQueryHandler(ProductDataMapper productDataMapper,
                                        ProductRepository productRepository) {
        this.productDataMapper = productDataMapper;
        this.productRepository = productRepository;
    }

    @Transactional
    public TrackProductListResponse trackProductWithCategory(TrackProductListQuery trackProductListQuery) {
        Optional<List<Product>> resultProductList =
                productRepository.findByProductCategory(trackProductListQuery.getProductCategoryList());

        if (resultProductList.isEmpty()) {
            log.warn("Could not fine product with product category: {}", trackProductListQuery.getProductCategoryList());
            throw new ProductNotFoundException("Could not fine product with product category: "
                    + trackProductListQuery.getProductCategoryList());
        }
        return productDataMapper.productListToTrackProductListResponse(resultProductList.get());
    }
}
