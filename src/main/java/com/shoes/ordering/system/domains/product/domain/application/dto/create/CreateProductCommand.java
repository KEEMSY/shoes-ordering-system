package com.shoes.ordering.system.domains.product.domain.application.dto.create;

import com.shoes.ordering.system.domains.common.validation.SelfValidating;
import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.product.domain.core.entity.ProductImage;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductImageId;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CreateProductCommand extends SelfValidating<CreateProductCommand> {

    @NotNull private final String name;
    @NotNull private final ProductCategory productCategory;
    @NotNull private final String description;
    @NotNull private final Money price;
    private final @NotNull List<String> productImages;

    private CreateProductCommand(Builder builder) {
        name = builder.name;
        productCategory = builder.productCategory;
        description = builder.description;
        price = builder.price;
        productImages = builder.productImages;

        this.validateSelf(this);
    }

    public String getName() {
        return name;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public String getDescription() {
        return description;
    }

    public Money getPrice() {
        return price;
    }

    public List<ProductImage> getProductImages() {
        return productImages.stream()
                .map(url -> new ProductImage(new ProductImageId(UUID.randomUUID()), url))
                .collect(Collectors.toList());
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private @NotNull String name;
        private @NotNull ProductCategory productCategory;
        private @NotNull String description;
        private @NotNull Money price;
        private @NotNull List<String> productImages;

        private Builder() {
        }

        public Builder name(@NotNull String val) {
            name = val;
            return this;
        }

        public Builder productCategory(@NotNull ProductCategory val) {
            productCategory = val;
            return this;
        }

        public Builder description(@NotNull String val) {
            description = val;
            return this;
        }

        public Builder price(@NotNull Money val) {
            price = val;
            return this;
        }

        public Builder productImages(@NotNull List<String> val) {
            productImages = val;
            return this;
        }

        public CreateProductCommand build() {
            return new CreateProductCommand(this);
        }
    }
}
