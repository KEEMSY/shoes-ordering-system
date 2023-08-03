package com.shoes.ordering.system.domains.product.domain.application.dto.track;

import com.shoes.ordering.system.domains.common.validation.SelfValidating;
import com.shoes.ordering.system.domains.product.domain.application.dto.ProductDTOException;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Getter
public class TrackProductListQuery extends SelfValidating<TrackProductListQuery> {

    @NotNull private final List<ProductCategory> productCategoryList;

    private TrackProductListQuery(Builder builder) {
        productCategoryList = builder.productCategoryList;
        this.validateSelf(this);
        validateProductCategoryList();
    }

    private void validateProductCategoryList() {
        Set<ProductCategory> unableProductCategories = EnumSet.of(ProductCategory.DISABLING);

        for (ProductCategory category : productCategoryList) {
            if (unableProductCategories.contains(category)) {
                throw new ProductDTOException("Unable ProductCategories: " + category);
            }
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private @NotNull List<ProductCategory> productCategoryList;

        private Builder() {
        }

        public Builder productCategoryList(@NotNull List<String> val) {

            if (val == null) throw new ProductDTOException("Invalid product categories: null");

            List<ProductCategory> validProductCategoryList = new ArrayList<>();
            List<String> invalidCategories = new ArrayList<>();

            for (String productCategory : val) {
                try {
                    ProductCategory category = ProductCategory.valueOf(productCategory);
                    validProductCategoryList.add(category);
                } catch (IllegalArgumentException ex) {
                    invalidCategories.add(productCategory);
                }
            }

            if (!invalidCategories.isEmpty()) {
                String errorMessage = "Invalid product categories: " + String.join(", ", invalidCategories);
                throw new ProductDTOException(errorMessage);
            }

            productCategoryList = validProductCategoryList;
            return this;
        }

        public TrackProductListQuery build() {
            return new TrackProductListQuery(this);
        }
    }
}
