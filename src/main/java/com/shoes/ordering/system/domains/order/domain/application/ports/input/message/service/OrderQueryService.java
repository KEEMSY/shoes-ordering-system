package com.shoes.ordering.system.domains.order.domain.application.ports.input.message.service;

import com.shoes.ordering.system.domains.order.domain.application.dto.track.TrackOrderQuery;
import com.shoes.ordering.system.domains.order.domain.application.dto.track.TrackOrderResponse;

import javax.validation.Valid;

public interface OrderQueryService {
    TrackOrderResponse trackOrder(@Valid TrackOrderQuery trackOrderQuery);
}
