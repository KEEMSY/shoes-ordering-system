package com.shoes.ordering.system.domains.product.adapter.out.dataaccess.respository.impl;

import com.shoes.ordering.system.TestConfiguration;
import com.shoes.ordering.system.common.redis.service.RedisService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = TestConfiguration.class)
@DirtiesContext
@Testcontainers
@ContextConfiguration(initializers = ProductAppliedRedisRepositoryImplTest.ContainerPropertyInitializer.class)
class ProductAppliedRedisRepositoryImplTest {
    private static final String REDIS_IMAGE = "redis:latest";
    private static final int REDIS_PORT = 6379;
    @Autowired
    private RedisService redisService;
    @Autowired
    private ProductAppliedRedisRepositoryImpl productAppliedRepository;

    @Container
    private static final GenericContainer<?> redisContainer =
            new GenericContainer(DockerImageName.parse(REDIS_IMAGE))
                    .withExposedPorts(REDIS_PORT)
                    .waitingFor(Wait.forListeningPort());

    @BeforeAll
    static void beforeAll() {
        System.setProperty("spring.redis.host", redisContainer.getHost());
        System.setProperty("spring.redis.port", redisContainer.getFirstMappedPort().toString());
    }

    @Test
    @DisplayName("addMemberIdToLimitedProduct 정상 확인: MemberId 정상 추가 확인")
    public void addMemberIdToLimitedProduct_SuccessTest() {
        // given
        UUID productId = UUID.randomUUID();
        UUID memberId = UUID.randomUUID();

        // when
        boolean result = productAppliedRepository.addMemberIdToLimitedProduct(productId, memberId);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("addMemberIdToLimitedProduct 에러 확인: 이미 MemberId 가 추가된 경우")
    public void addMemberIdToLimitedProduct_FailureTest() {
        // given
        UUID productId = UUID.randomUUID();
        UUID memberId = UUID.randomUUID();
        String productIdStr = productId.toString();
        String memberIdStr = memberId.toString();
        redisService.setSetOps(productIdStr, memberIdStr);

        // when
        boolean result = productAppliedRepository.addMemberIdToLimitedProduct(productId, memberId);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("addMemberIdToLimitedProduct 에러확인: 동시에 요청을 할 경우")
    void addMemberIdToLimitedProduct_MultiMember() throws InterruptedException {
        // given
        int threadCount = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        UUID memberId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        AtomicInteger successfulCalls = new AtomicInteger(0);

        // when
        for(int i = 0; i < threadCount; i ++) {
            executorService.submit(() -> {
                try {
                    boolean success = productAppliedRepository.addMemberIdToLimitedProduct(productId, memberId);
                    if (success) {
                        successfulCalls.incrementAndGet();
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        // then
        assertThat(successfulCalls.intValue()).isEqualTo(1);
    }

    static class ContainerPropertyInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext context) {
            TestPropertyValues.of("container.ports=" + redisContainer.getMappedPort(6379))
                    .applyTo(context.getEnvironment());
        }
    }
}