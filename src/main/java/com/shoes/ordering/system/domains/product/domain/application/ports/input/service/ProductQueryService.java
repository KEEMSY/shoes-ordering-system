package com.shoes.ordering.system.domains.product.domain.application.ports.input.service;

import com.shoes.ordering.system.domains.product.domain.application.dto.track.TrackProductQuery;
import com.shoes.ordering.system.domains.product.domain.application.dto.track.TrackProductResponse;

import javax.validation.Valid;

public interface ProductQueryService {
    TrackProductResponse trackProduct(@Valid TrackProductQuery trackProductQuery);
}
