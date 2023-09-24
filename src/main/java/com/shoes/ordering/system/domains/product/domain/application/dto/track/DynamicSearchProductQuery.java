package com.shoes.ordering.system.domains.product.domain.application.dto.track;

import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.product.domain.application.dto.ProductDTOException;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class DynamicSearchProductQuery {
    private final String name;
    private final List<ProductCategory> productCategoryList;
    private final Money minPrice;
    private final Money maxPrice;

    private DynamicSearchProductQuery(Builder builder) {
        builder.setDefaultValue();

        builder.validName();
        builder.validMinPrice();
        builder.validMaxPrice();

        name = builder.name;
        productCategoryList = builder.productCategoryList;
        minPrice = builder.minPrice;
        maxPrice = builder.maxPrice;
    }



    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private final List<ProductCategory> DEFAULT_PRODUCTCATEGORY_LIST = Arrays.asList(ProductCategory.values());
        private final Money DEFAULT_MIN_PRICE = new Money(new BigDecimal("0.01"));
        private final Money DEFAULT_MAX_PRICE = new Money(new BigDecimal(Integer.MAX_VALUE));
        private final int NAME_LENGTH_LIMIT = 255;

        private String name;
        private List<ProductCategory> productCategoryList;
        private Money minPrice;
        private Money maxPrice;

        private Builder() {
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder productCategoryList(List<String> val) {
            if (val == null ) {
                productCategoryList = null;
                return this;
            }
            productCategoryList = parseProductCategorList(val);

            return this;
        }

        public Builder minPrice(Money val) {
            minPrice = val;
            return this;
        }

        public Builder maxPrice(Money val) {
            maxPrice = val;
            return this;
        }

        public DynamicSearchProductQuery build() {
            return new DynamicSearchProductQuery(this);
        }

        private List<ProductCategory> parseProductCategorList(List<String> val) {
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
            return validProductCategoryList;
        }

        private void validMinPrice() {
            if (!minPrice.isGreaterThanZero()) {
                throw new ProductDTOException("The minPrice should be greater then Zero!");
            }
            if (minPrice.isGreaterThan(maxPrice)) {
                throw new ProductDTOException("The minPrice can't be greater then maxPrice!");
            }
        }

        private void validMaxPrice() {
            if (!maxPrice.isGreaterThanZero()) {
                throw new ProductDTOException("The maxPrice should be greater then Zero!");
            }
            if (!maxPrice.isGreaterThan(minPrice)) {
                throw new ProductDTOException("The manPrice Should be greater then minPrice!");
            }
        }

        private void setDefaultValue() {
            if (productCategoryList == null) {
                productCategoryList = DEFAULT_PRODUCTCATEGORY_LIST;
            }

            if (minPrice == null) {
                minPrice = DEFAULT_MIN_PRICE;
            }

            if (maxPrice == null) {
                maxPrice = DEFAULT_MAX_PRICE;
            }

        }

        public void validName() {
            if (name != null && name.length() > NAME_LENGTH_LIMIT) {
                throw new ProductDTOException("The name cannot exceed 255 characters.");
            }
        }
    }
}
