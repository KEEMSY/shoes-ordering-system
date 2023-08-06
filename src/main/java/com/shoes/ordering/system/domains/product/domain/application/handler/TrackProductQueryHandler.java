package com.shoes.ordering.system.domains.product.domain.application.handler;

import com.shoes.ordering.system.domains.product.domain.application.dto.track.TrackProductQuery;
import com.shoes.ordering.system.domains.product.domain.application.dto.track.TrackProductResponse;
import com.shoes.ordering.system.domains.product.domain.application.mapper.ProductDataMapper;
import com.shoes.ordering.system.domains.product.domain.application.ports.output.repository.ProductRepository;
import com.shoes.ordering.system.domains.product.domain.core.entity.Product;
import com.shoes.ordering.system.domains.product.domain.core.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
public class TrackProductQueryHandler {

    private final ProductDataMapper productDataMapper;
    private final ProductRepository productRepository;


    public TrackProductQueryHandler(ProductDataMapper productDataMapper,
                                    ProductRepository productRepository) {
        this.productDataMapper = productDataMapper;
        this.productRepository = productRepository;
    }

    @Transactional
    public TrackProductResponse trackProduct(TrackProductQuery trackProductQuery) {
        Optional<Product> resultProduct = productRepository.findByProductId(trackProductQuery.getProductId());

        if (resultProduct.isEmpty()) {
            log.warn("Could not find product with productId: {}", trackProductQuery.getProductId());
            throw new ProductNotFoundException("Could not find product with productId: " + trackProductQuery.getProductId());
        }
        return productDataMapper.productToTrackProductResponse(resultProduct.get());
    }

}
