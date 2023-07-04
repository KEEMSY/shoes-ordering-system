package com.shoes.ordering.system.domains.product.domain.application.dto.track;

import com.shoes.ordering.system.domains.common.validation.SelfValidating;
import com.shoes.ordering.system.domains.product.domain.application.dto.ProductDTOException;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;

import javax.validation.constraints.NotNull;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class TrackProductListQuery extends SelfValidating<TrackProductListQuery> {

    @NotNull private final List<ProductCategory> productCategoryList;

    private TrackProductListQuery(Builder builder) {
        productCategoryList = builder.productCategoryList;
        this.validateSelf(this);
        validateProductCategoryList();
    }

    private void validateProductCategoryList() {
        Set<ProductCategory> validProductCategories = EnumSet.allOf(ProductCategory.class);

        for (ProductCategory category : productCategoryList) {
            if (!validProductCategories.contains(category) || category == ProductCategory.DISABLING) {
                throw new ProductDTOException("Invalid product category: " + category);
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

        public Builder productCategoryList(@NotNull List<ProductCategory> val) {
            productCategoryList = val;
            return this;
        }

        public TrackProductListQuery build() {
            return new TrackProductListQuery(this);
        }
    }
}
