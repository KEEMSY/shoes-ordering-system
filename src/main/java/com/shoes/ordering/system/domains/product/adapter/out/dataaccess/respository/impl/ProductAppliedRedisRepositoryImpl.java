package com.shoes.ordering.system.domains.product.adapter.out.dataaccess.respository.impl;

import com.shoes.ordering.system.common.redis.service.RedisService;
import com.shoes.ordering.system.domains.product.adapter.out.dataaccess.respository.ProductAppliedRedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
@Slf4j
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
            log.warn("memberId: {} is already registered in productId: {}", memberId, productId);
            return false;
        } else {
            // Redis 집합에 memberId 추가
            redisService.setSetOps(productIdStr, memberIdStr);
            log.info("memberId: {} is registered in productId: {}", memberId, productId);
            return true;
        }
    }
}
