package com.shoes.ordering.system.domains.order.domain.application;

import com.shoes.ordering.system.domains.order.domain.application.dto.track.TrackOrderQuery;
import com.shoes.ordering.system.domains.order.domain.application.dto.track.TrackOrderResponse;
import com.shoes.ordering.system.domains.order.domain.application.handler.TrackOrderQueryHandler;
import com.shoes.ordering.system.domains.order.domain.application.ports.input.message.service.OrderQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderQueryServiceImpl implements OrderQueryService {
    private final TrackOrderQueryHandler trackOrderQueryHandler;

    public OrderQueryServiceImpl(TrackOrderQueryHandler trackOrderQueryHandler) {
        this.trackOrderQueryHandler = trackOrderQueryHandler;
    }

    @Override
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        return trackOrderQueryHandler.trackOrder(trackOrderQuery);
    }
}
