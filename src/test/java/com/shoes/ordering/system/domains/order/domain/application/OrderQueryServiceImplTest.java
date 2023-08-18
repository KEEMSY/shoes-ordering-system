package com.shoes.ordering.system.domains.order.domain.application;

import com.shoes.ordering.system.TestConfiguration;
import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.common.valueobject.StreetAddress;
import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberId;
import com.shoes.ordering.system.domains.order.domain.application.dto.track.TrackOrderQuery;
import com.shoes.ordering.system.domains.order.domain.application.dto.track.TrackOrderResponse;
import com.shoes.ordering.system.domains.order.domain.application.ports.input.message.service.OrderQueryService;
import com.shoes.ordering.system.domains.order.domain.application.ports.output.repository.OrderRepository;
import com.shoes.ordering.system.domains.order.domain.core.entity.Order;
import com.shoes.ordering.system.domains.order.domain.core.entity.OrderItem;
import com.shoes.ordering.system.domains.order.domain.core.exception.OrderNotFoundException;
import com.shoes.ordering.system.domains.order.domain.core.valueobject.OrderStatus;
import com.shoes.ordering.system.domains.order.domain.core.valueobject.TrackingId;
import com.shoes.ordering.system.domains.product.domain.core.entity.Product;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = TestConfiguration.class)
class OrderQueryServiceImplTest {

    @Autowired
    private OrderQueryService orderQueryService;
    @Autowired
    private OrderRepository orderRepository;


    private UUID targetOrderTrackingId;
    private final UUID MEMBER_ID = UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb41");
    private BigDecimal totalPrice = new BigDecimal("00.00");

    @Test
    @DisplayName("정상 trackOrder 테스트")
    void trackOrderTest() {
        // given
        targetOrderTrackingId = UUID.randomUUID();

        TrackOrderQuery trackOrderQuery = createTrackOrderQuery(targetOrderTrackingId);

        OrderItem orderItem = createOrderItem(1, new BigDecimal("50.00"));
        Order targetOrder = createOrder(totalPrice, List.of(orderItem));

        when(orderRepository.findByTrackingId(any(TrackingId.class))).thenReturn(Optional.of(targetOrder));

        // when
        TrackOrderResponse result = orderQueryService.trackOrder(trackOrderQuery);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getOrderTrackingId()).isEqualTo(targetOrderTrackingId);
    }

    @Test
    @DisplayName("정상 trackOrder 에러 테스트: 존재하지 않는 trackingId")
    void trackOrderErrorTest() {
        // given
        UUID unknownTrackingID = UUID.randomUUID();
        TrackOrderQuery wrongTrackOrderQuery = createTrackOrderQuery(unknownTrackingID);

        when(orderRepository.findByTrackingId(any(TrackingId.class)))
                .thenReturn(Optional.ofNullable(null));

        // when, then
        assertThatThrownBy(() -> orderQueryService.trackOrder(wrongTrackOrderQuery))
                .isInstanceOf(OrderNotFoundException.class);
    }

    private Order createOrder(BigDecimal totalPrice, List<OrderItem> items) {
        return Order.builder()
                .trackingId(new TrackingId(targetOrderTrackingId))
                .memberId(new MemberId(MEMBER_ID))
                .deliveryAddress(new StreetAddress(UUID.randomUUID(), "123 Street", "99999", "City"))
                .price(new Money(totalPrice))
                .items(items)
                .orderStatus(OrderStatus.PENDING)
                .build();
    }

    private OrderItem createOrderItem(int quantity, BigDecimal productPrice) {
        Product product = Product.builder()
                .productId(new ProductId(UUID.randomUUID()))
                .name("Test name")
                .productCategory(ProductCategory.SHOES)
                .description("Test Description")
                .price(new Money(productPrice))
                .build();

        totalPrice = totalPrice.add(productPrice.multiply(BigDecimal.valueOf(quantity)));

        return OrderItem.builder()
                .product(product)
                .quantity(quantity)
                .price(new Money(productPrice))
                .subTotal(new Money(productPrice.multiply(BigDecimal.valueOf(quantity))))
                .build();
    }

    private TrackOrderQuery createTrackOrderQuery(UUID orderTrackingId) {
        return TrackOrderQuery.builder()
                .orderTrackingId(orderTrackingId)
                .build();
    }

    private TrackOrderResponse createTrackOrderResponse(UUID orderTrackingId, OrderStatus orderStatus) {
        return TrackOrderResponse.builder()
                .orderTrackingId(orderTrackingId)
                .orderStatus(orderStatus)
                .failureMessages(new ArrayList<>())
                .build();
    }


}