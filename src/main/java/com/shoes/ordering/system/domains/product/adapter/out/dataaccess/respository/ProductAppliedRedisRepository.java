package com.shoes.ordering.system.domains.product.adapter.out.dataaccess.respository;

import java.util.UUID;

public interface ProductAppliedRedisRepository {
    boolean addMemberIdToLimitedProduct(UUID productId, UUID memberId);
}
