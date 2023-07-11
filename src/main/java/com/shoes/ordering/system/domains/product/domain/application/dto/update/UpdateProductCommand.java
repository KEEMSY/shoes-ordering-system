package com.shoes.ordering.system.domains.product.domain.application.dto.update;

import com.shoes.ordering.system.domains.common.validation.SelfValidating;
import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.product.domain.core.entity.ProductImage;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductId;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductImageId;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class UpdateProductCommand extends SelfValidating<UpdateProductCommand> {

    @NotNull private final UUID productId;
    @NotNull private final String name;
    @NotNull private final ProductCategory productCategory;
    @NotNull private final String description;
    @NotNull private final Money price;
//    @NotNull private final  List<String> productImages;

    private UpdateProductCommand(Builder builder) {
        productId = builder.productId;
        name = builder.name;
        productCategory = builder.productCategory;
        description = builder.description;
        price = builder.price;
//        productImages = builder.productImages;

        this.validateSelf(this);
    }


//    public List<ProductImage> getProductImages() {
//        return productImages.stream()
//                .map(inputUrl -> ProductImage.builder()
//                        .productImageId(get)
//                        .productImageId(new ProductImageId(UUID.randomUUID()))
//                        .productId(new ProductId(productId))
//                        .productImageUrl(inputUrl)
//                        .build())
//                .collect(Collectors.toList());
//    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private @NotNull UUID productId;
        private @NotNull String name;
        private @NotNull ProductCategory productCategory;
        private @NotNull String description;
        private @NotNull Money price;
//        private @NotNull List<String> productImages;

        private Builder() {
        }

        public Builder productId(@NotNull UUID val) {
            productId = val;
            return this;
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

//        public Builder productImages(@NotNull List<String> val) {
//            productImages = val;
//            return this;
//        }

        public UpdateProductCommand build() {
            return new UpdateProductCommand(this);
        }
    }
}
