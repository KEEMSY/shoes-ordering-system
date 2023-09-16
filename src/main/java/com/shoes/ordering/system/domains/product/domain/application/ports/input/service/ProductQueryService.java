package com.shoes.ordering.system.domains.product.domain.application.ports.input.service;

import com.shoes.ordering.system.domains.product.domain.application.dto.track.*;

import javax.validation.Valid;

public interface ProductQueryService {
    TrackProductResponse trackProduct(@Valid TrackProductQuery trackProductQuery);
    TrackProductListResponse trackProductWithCategory(@Valid TrackProductListQuery trackProductListQuery);
    TrackProductListResponse searchProduct(@Valid DynamicSearchProductQuery searchProductQuery);
}
