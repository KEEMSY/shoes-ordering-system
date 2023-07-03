package com.shoes.ordering.system.domains.product.domain.application;

import com.shoes.ordering.system.domains.product.domain.application.dto.track.TrackProductQuery;
import com.shoes.ordering.system.domains.product.domain.application.dto.track.TrackProductResponse;
import com.shoes.ordering.system.domains.product.domain.application.ports.input.service.ProductQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
public class ProductQueryServiceImpl implements ProductQueryService {
    @Override
    public TrackProductResponse trackProduct(TrackProductQuery trackProductQuery) {
        return null;
    }
}
