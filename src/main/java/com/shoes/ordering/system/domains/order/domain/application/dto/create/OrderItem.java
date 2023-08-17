package com.shoes.ordering.system.domains.order.domain.application.dto.create;

import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class OrderItem {
    @NotNull
    private final UUID productId;
    @NotNull
    private final String name;
    @NotNull
    private final ProductCategory productCategory;
    @NotNull
    private final String description;
    @NotNull
    private final Integer quantity;
    @NotNull
    private final BigDecimal price;
    @NotNull
    private final BigDecimal subTotal;
}
