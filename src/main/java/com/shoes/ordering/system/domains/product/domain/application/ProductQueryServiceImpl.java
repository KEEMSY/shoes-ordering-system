package com.shoes.ordering.system.domains.product.domain.application;

import com.shoes.ordering.system.domains.product.domain.application.dto.track.TrackProductListQuery;
import com.shoes.ordering.system.domains.product.domain.application.dto.track.TrackProductListResponse;
import com.shoes.ordering.system.domains.product.domain.application.dto.track.TrackProductQuery;
import com.shoes.ordering.system.domains.product.domain.application.dto.track.TrackProductResponse;
import com.shoes.ordering.system.domains.product.domain.application.handler.TrackProductListQueryHandler;
import com.shoes.ordering.system.domains.product.domain.application.handler.TrackProductQueryHandler;
import com.shoes.ordering.system.domains.product.domain.application.ports.input.service.ProductQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Slf4j
@Validated
@Service
public class ProductQueryServiceImpl implements ProductQueryService {

    private final TrackProductQueryHandler trackProductQueryHandler;
    private final TrackProductListQueryHandler trackProductListQueryHandler;

    public ProductQueryServiceImpl(TrackProductQueryHandler trackProductQueryHandler,
                                   TrackProductListQueryHandler trackProductListQueryHandler) {
        this.trackProductQueryHandler = trackProductQueryHandler;
        this.trackProductListQueryHandler = trackProductListQueryHandler;
    }

    @Override
    public TrackProductResponse trackProduct(TrackProductQuery trackProductQuery) {
        return trackProductQueryHandler.trackProduct(trackProductQuery);
    }
    @Override
    public List<TrackProductListResponse> trackProductWithCategory(TrackProductListQuery trackProductListQuery) {
        return null;
    }

}
