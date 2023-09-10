package com.shoes.ordering.system.domains.product.adapter.out.dataaccess.respository.impl;

import com.shoes.ordering.system.common.redis.service.RedisService;
import com.shoes.ordering.system.domains.product.adapter.out.dataaccess.respository.ProductAppliedRedisRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public class ProductAppliedRedisRepositoryImpl implements ProductAppliedRedisRepository {
    private final RedisService redisService;

    public ProductAppliedRedisRepositoryImpl(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public boolean addMemberIdToLimitedProduct(UUID productId, UUID memberId) {
        String productIdStr = productId.toString();
        String memberIdStr = memberId.toString();

        // Redis 집합에 memberId가 있는지 확인
        Long memberIdExists = redisService.setSetOps(productIdStr, memberIdStr);
        if (memberIdExists != 1) {
            return false;
        } else {
            // Redis 집합에 memberId 추가
            redisService.setSetOps(productIdStr, memberIdStr);
            return true;
        }
    }
}
