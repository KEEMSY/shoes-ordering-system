package com.shoes.ordering.system.domains.product.adapter.out.dataaccess.respository.impl;

import com.shoes.ordering.system.TestConfiguration;
import com.shoes.ordering.system.common.redis.service.RedisService;
import com.shoes.ordering.system.domains.product.adapter.out.dataaccess.respository.ProductAppliedRedisRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = TestConfiguration.class)
@DirtiesContext
@Testcontainers
class ProductAppliedRedisRepositoryImplTest {
    private static final String REDIS_IMAGE = "redis:latest";
    private static final int REDIS_PORT = 6379;
    @Autowired
    private RedisService redisService;
    @Autowired
    private ProductAppliedRedisRepository productAppliedRepository;

    @Container
    private static final GenericContainer<?> redisContainer =
            new GenericContainer(DockerImageName.parse(REDIS_IMAGE))
                    .withExposedPorts(REDIS_PORT);

    @BeforeAll
    static void beforeAll() {
        System.setProperty("spring.redis.host", redisContainer.getHost());
        System.setProperty("spring.redis.port", redisContainer.getFirstMappedPort().toString());
    }

    @Test
    @DisplayName("addMemberIdToLimitedProduct 정상 확인: MemberId 정상 추가 확인")
    public void addUserIdToProduct_SuccessTest() {
        // given
        UUID productId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        // when
        boolean result = productAppliedRepository.addMemberIdToLimitedProduct(productId, userId);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("addMemberIdToLimitedProduct 에러 확인: 이미 MemberId 가 추가된 경우")
    public void addUserIdToProduct_FailureTest() {
        // given
        UUID productId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String productIdStr = productId.toString();
        String memberIdStr = userId.toString();
        redisService.setSetOps(productIdStr, memberIdStr);

        // when
        boolean result = productAppliedRepository.addMemberIdToLimitedProduct(productId, userId);

        // then
        assertThat(result).isFalse();
    }
}