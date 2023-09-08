package com.shoes.ordering.system.common.redis.service;

import com.shoes.ordering.system.TestConfiguration;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(classes = TestConfiguration.class)
@Testcontainers
public class RedisServiceTest {
    private static final String REDIS_IMAGE = "redis:latest";
    private static final int REDIS_PORT = 6379;
    @Autowired
    private RedisService redisService;


    @Container
    private static final GenericContainer<?> redisContainer =
            new GenericContainer(DockerImageName.parse(REDIS_IMAGE))
                    .withExposedPorts(REDIS_PORT);

    @BeforeAll
    static void beforeAll() {
        // Redis 컨테이너가 실행될 때 초기 설정이 필요한 경우 여기에서 설정 가능
        System.setProperty("spring.redis.host", redisContainer.getHost());
        System.setProperty("spring.redis.port", redisContainer.getFirstMappedPort().toString());
    }

    @Test
    @DisplayName("setStringOps 성공 테스트: 문자열 저장")
    public void setStringOpsTest() {
        String key = "testKey";
        String value = "testValue";
        redisService.setStringOps(key, value, 1, TimeUnit.MINUTES);

        String retrievedValue = redisService.getStringOps(key);

        assertThat(value).isEqualTo(retrievedValue);
    }
    @Test
    @DisplayName("getStringOps 테스트: 존재하지 않는 키로 조회하면 null 을 반환해야한다.")
    public void getStringOpsFailureTest() {
        String key = "nonExistentKey";
        String retrievedValue = redisService.getStringOps(key);
        assertThat(retrievedValue).isNull();
    }


    @Test
    @DisplayName("setListOps 성공 테스트: 리스트 저장")
    public void setListOpsTest() {
        String key = "testList";
        List<String> values = Arrays.asList("value1", "value2", "value3");
        redisService.setListOps(key, values);

        List<String> retrievedValues = redisService.getListOps(key);
        assertThat(values).isEqualTo(retrievedValues);
    }
    @Test
    @DisplayName("getListOps 테스트: 존재하지 않는 키로 조회하면 빈 리스트를 반환해야한다.")
    public void getListOpsFailureTest() {
        String key = "nonExistentList";
        List<String> retrievedValues = redisService.getListOps(key);
        assertThat(retrievedValues.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("setHashOps 성공 테스트: 해시맵 저장")
    public void setHashOpsTest() {
        String key = "testHash";
        HashMap<String, String> values = new HashMap<>();
        values.put("field1", "value1");
        values.put("field2", "value2");

        redisService.setHashOps(key, values);

        String hashValue = redisService.getHashOps(key, "field1");
        assertThat("value1").isEqualTo(hashValue);
    }
    @Test
    @DisplayName("getHashOps 테스트: 존재하지 않는 키나 필드로 조회하면 빈 문자열을 반환해야 한다.")
    public void getHashOpsTest() {
        String key = "nonExistentHash";
        String hashKey = "nonExistentField";
        String hashValue = redisService.getHashOps(key, hashKey);
        assertThat(hashValue).isEmpty();
    }

    @Test
    @DisplayName("setSetOps 성공 테스트: 집합 저장")
    public void setSetOpsTest() {
        String key = "testSet";
        String[] values = {"value1", "value2", "value3"};

        redisService.setSetOps(key, values);

        Set<String> retrievedValues = redisService.getSetOps(key);
        assertThat(new HashSet<>(Arrays.asList(values))).isEqualTo(retrievedValues);
    }
    @Test
    @DisplayName("getSetOps 테스트: 존재하지 않는 키로 조회하면 빈 집합을 반환해야 한다.")
    public void getSetOpsTest() {
        String key = "nonExistentSet";
        Set<String> retrievedValues = redisService.getSetOps(key);
        assertThat(retrievedValues.isEmpty()).isTrue();
    }
}