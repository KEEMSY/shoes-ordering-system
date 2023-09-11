package com.shoes.ordering.system.domains.member.messaging.publisher;

import com.shoes.ordering.system.CustomKafkaTestConfig;
import com.shoes.ordering.system.TestConfiguration;
import com.shoes.ordering.system.common.kafka.model.CreateMemberRequestAvroModel;
import com.shoes.ordering.system.domains.common.valueobject.StreetAddress;
import com.shoes.ordering.system.domains.member.domain.core.entity.Member;
import com.shoes.ordering.system.domains.member.domain.core.event.MemberCreatedEvent;
import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberKind;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@EmbeddedKafka(partitions = 1, topics = {"create-member-request"})
@SpringBootTest(classes = TestConfiguration.class)
@DirtiesContext
class CreateMemberKafkaMessagePublisherTest {

    @Autowired
    private CreateMemberKafkaMessagePublisher createMemberKafkaMessagePublisher;
    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;
    @Autowired
    private CustomKafkaTestConfig customKafkaTestConfig;
    private Long TIMEOUT_LIMIT = 1000L;

    @Test
    @DisplayName("정상 publish 확인: MemberCreatedEvent 발행 확인")
    void publishTest() {
        // given
        String targetTopic = "create-member-request";
        Map<String, Object> consumerProps = customKafkaTestConfig.createDefaultConsumerProps(embeddedKafkaBroker);

        DefaultKafkaConsumerFactory<String, CreateMemberRequestAvroModel> consumerFactory
                = new DefaultKafkaConsumerFactory<>(consumerProps);
        Consumer<String, CreateMemberRequestAvroModel> consumer = consumerFactory.createConsumer();
        // when
        MemberCreatedEvent memberCreatedEvent = createMemberCreatedEvent();
        createMemberKafkaMessagePublisher.publish(memberCreatedEvent);

        // then

        embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumer, targetTopic);
        ConsumerRecord<String, CreateMemberRequestAvroModel> record
                = KafkaTestUtils.getSingleRecord(consumer, targetTopic, TIMEOUT_LIMIT);

        assertThat(record).isNotNull();
        assertThat(record.topic()).isEqualTo(targetTopic);
    }

    private MemberCreatedEvent createMemberCreatedEvent() {
        Member member =  Member.builder()
                .memberKind(MemberKind.CUSTOMER)
                .phoneNumber("0101231234")
                .name("TestName")
                .password("TestPassword123!@#")
                .email("testEmail@test.com")
                .address(new StreetAddress(UUID.randomUUID(),"123 Street", "99999", "City"))
                .build();

        member.initializeMember();

        return new MemberCreatedEvent(member, ZonedDateTime.now());
    }
}