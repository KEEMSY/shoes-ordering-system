package com.shoes.ordering.system.domains.product.adapter.out.dataaccess.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_images")
@Entity
public class ProductImageEntity {
    @Id
    private UUID productImageId;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PRODUCT_ID")
    private ProductEntity product;
    private String productImageUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductImageEntity)) return false;
        ProductImageEntity that = (ProductImageEntity) o;
        return productImageId.equals(that.productImageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productImageId);
    }
}
