package com.shoes.ordering.system.domains.order.adapter.out.dataaccess.mapper;

import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.common.valueobject.StreetAddress;
import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberId;
import com.shoes.ordering.system.domains.order.adapter.out.dataaccess.entity.OrderAddressEntity;
import com.shoes.ordering.system.domains.order.adapter.out.dataaccess.entity.OrderEntity;
import com.shoes.ordering.system.domains.order.adapter.out.dataaccess.entity.OrderItemEntity;
import com.shoes.ordering.system.domains.order.domain.core.entity.Order;
import com.shoes.ordering.system.domains.order.domain.core.entity.OrderItem;
import com.shoes.ordering.system.domains.order.domain.core.valueobject.OrderId;
import com.shoes.ordering.system.domains.order.domain.core.valueobject.OrderItemId;
import com.shoes.ordering.system.domains.order.domain.core.valueobject.TrackingId;
import com.shoes.ordering.system.domains.product.adapter.out.dataaccess.entity.ProductEntity;
import com.shoes.ordering.system.domains.product.domain.core.entity.Product;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductId;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.shoes.ordering.system.domains.order.domain.core.entity.Order.FAILURE_MESSAGE_DELIMITER;

@Component
public class OrderDataAccessMapper {
    public Order orderEntityToOrder(OrderEntity orderEntity) {
        return Order.builder()
                .orderId(new OrderId(orderEntity.getId()))
                .memberId(new MemberId(orderEntity.getMemberId()))
                .deliveryAddress(addressEntityToDeliveryAddress(orderEntity.getAddress()))
                .price(new Money(orderEntity.getPrice()))
                .items(orderItemEntitiesToOrderItems(orderEntity.getItems()))
                .trackingId(new TrackingId(orderEntity.getTrackingId()))
                .orderStatus(orderEntity.getOrderStatus())
                .failureMessages(orderEntity.getFailureMessages().isEmpty() ? new ArrayList<>() :
                        new ArrayList<>(Arrays.asList(orderEntity.getFailureMessages()
                                .split(FAILURE_MESSAGE_DELIMITER))))
                .build();
    }

    private List<OrderItem> orderItemEntitiesToOrderItems(List<OrderItemEntity> items) {
        return items.stream()
                .map(orderItemEntity -> OrderItem.builder()
                        .orderItemId(new OrderItemId(orderItemEntity.getId()))
                        .product(ProductEntityToProduct(orderItemEntity.getProduct()))
                        .price(new Money(orderItemEntity.getPrice()))
                        .quantity(orderItemEntity.getQuantity())
                        .subTotal(new Money(orderItemEntity.getSubTotal()))
                        .build())
                .collect(Collectors.toList());
    }

    private Product ProductEntityToProduct(ProductEntity productEntity) {
        return Product.builder()
                .productId(new ProductId(productEntity.getProductId()))
                .name(productEntity.getName())
                .price(new Money(productEntity.getPrice()))
                .description(productEntity.getDescription())
                .productCategory(productEntity.getProductCategory())
                .build();
    }

    private StreetAddress addressEntityToDeliveryAddress(OrderAddressEntity address) {
        return new StreetAddress(address.getId(),
                address.getStreet(),
                address.getPostalCode(),
                address.getCity());
    }

    public OrderEntity orderToOrderEntity(Order order) {
        OrderEntity orderEntity = OrderEntity.builder()
                .id(order.getId().getValue())
                .memberId(order.getMemberId().getValue())
                .trackingId(order.getTrackingId().getValue())
                .address(deliveryAddressToAddressEntity(order.getDeliveryAddress()))
                .price(order.getPrice().getAmount())
                .items(orderItemsToOrderItemEntities(order.getItems()))
                .orderStatus(order.getOrderStatus())
                .failureMessages(order.getFailureMessages() != null ?
                        String.join(FAILURE_MESSAGE_DELIMITER, order.getFailureMessages()) : "")
                .build();
        orderEntity.getAddress().setOrder(orderEntity);
        orderEntity.getItems().forEach(orderItemEntity -> orderItemEntity.setOrder(orderEntity));

        return orderEntity;
    }

    private List<OrderItemEntity> orderItemsToOrderItemEntities(List<OrderItem> items) {
        return items.stream()
                .map(orderItem -> OrderItemEntity.builder()
                        .id(orderItem.getId().getValue())
                        .product(productToProductEntity(orderItem.getProduct()))
                        .price(orderItem.getPrice().getAmount())
                        .quantity(orderItem.getQuantity())
                        .subTotal(orderItem.getSubTotal().getAmount())
                        .build())
                .collect(Collectors.toList());
    }

    public ProductEntity productToProductEntity(Product product) {
        return  ProductEntity.builder()
                .productId(product.getId().getValue())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice().getAmount())
                .productCategory(product.getProductCategory())
                .build();
    }

    private OrderAddressEntity deliveryAddressToAddressEntity(StreetAddress deliveryAddress) {
        return OrderAddressEntity.builder()
                .id(deliveryAddress.getId())
                .street(deliveryAddress.getStreet())
                .postalCode(deliveryAddress.getPostalCode())
                .city(deliveryAddress.getCity())
                .build();
    }
}
