package com.shoes.ordering.system.domains.order.adapter.out.dataaccess.entity;

import com.shoes.ordering.system.domains.product.adapter.out.dataaccess.entity.ProductEntity;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(OrderItemEntityId.class)
@Table(name = "order_items")
@Entity
public class OrderItemEntity {
    @Id
    private Long id;
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ORDER_ID")
    private OrderEntity order;
    @ManyToOne(cascade = CascadeType.DETACH, optional = false)
    @JoinColumn(name = "PRODUCT_ID")
    private ProductEntity product;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal subTotal;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItemEntity)) return false;
        OrderItemEntity that = (OrderItemEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
