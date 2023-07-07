package com.shoes.ordering.system.domains.product.adapter.out.dataaccess.entity;

import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
@Entity
public class ProductEntity {

    @Id
    private UUID productId;
    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private ProductCategory productCategory;
    @OneToMany(mappedBy = "products", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImageEntity> productImages;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductEntity)) return false;
        ProductEntity that = (ProductEntity) o;
        return productId.equals(that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}
