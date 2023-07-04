package com.shoes.ordering.system.domains.product.domain.application.ports.input.service;

import com.shoes.ordering.system.domains.product.domain.application.dto.track.TrackProductListQuery;
import com.shoes.ordering.system.domains.product.domain.application.dto.track.TrackProductListResponse;
import com.shoes.ordering.system.domains.product.domain.application.dto.track.TrackProductQuery;
import com.shoes.ordering.system.domains.product.domain.application.dto.track.TrackProductResponse;

import javax.validation.Valid;
import java.util.List;

public interface ProductQueryService {
    TrackProductResponse trackProduct(@Valid TrackProductQuery trackProductQuery);
    List<TrackProductListResponse> trackProductWithCategory(@Valid TrackProductListQuery trackProductListQuery);
}
