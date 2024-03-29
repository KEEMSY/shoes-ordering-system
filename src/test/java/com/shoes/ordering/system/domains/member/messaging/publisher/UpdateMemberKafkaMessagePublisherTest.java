package com.shoes.ordering.system.domains.member.messaging.publisher;

import com.shoes.ordering.system.CustomKafkaTestConfig;
import com.shoes.ordering.system.TestConfiguration;
import com.shoes.ordering.system.common.kafka.model.UpdateMemberRequestAvroModel;
import com.shoes.ordering.system.domains.common.valueobject.StreetAddress;
import com.shoes.ordering.system.domains.member.domain.core.entity.Member;
import com.shoes.ordering.system.domains.member.domain.core.event.MemberUpdatedEvent;
import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberId;
import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberKind;
import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberStatus;
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


@DirtiesContext
@EmbeddedKafka(partitions = 1, topics = {"update-member-request"})
@SpringBootTest(classes = TestConfiguration.class)
class UpdateMemberKafkaMessagePublisherTest {

    @Autowired
    private UpdateMemberKafkaMessagePublisher updateMemberKafkaMessagePublisher;
    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;
    @Autowired
    private CustomKafkaTestConfig customKafkaTestConfig;

    private Long TIMEOUT_LIMIT = 1000L;


    @Test
    @DisplayName("정상 publish 확인: MemberUpdatedEvent 발행 확")
    void publishTest() {
        // given
        String targetTopic = "update-member-request";
        Map<String, Object> consumerProps = customKafkaTestConfig.createDefaultConsumerProps(embeddedKafkaBroker);

        DefaultKafkaConsumerFactory<String, UpdateMemberRequestAvroModel> consumerFactory
                = new DefaultKafkaConsumerFactory<>(consumerProps);
        Consumer<String, UpdateMemberRequestAvroModel> consumer = consumerFactory.createConsumer();

        // when
        MemberUpdatedEvent memberUpdatedEvent = createMemberUpdatedEvent();
        updateMemberKafkaMessagePublisher.publish(memberUpdatedEvent);

        // then
        embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumer, targetTopic);
        ConsumerRecord<String, UpdateMemberRequestAvroModel> record
                = KafkaTestUtils.getSingleRecord(consumer, targetTopic, TIMEOUT_LIMIT);

        assertThat(record).isNotNull();
        assertThat(record.topic()).isEqualTo(targetTopic);
    }

    private MemberUpdatedEvent createMemberUpdatedEvent() {
        Member member =  Member.builder()
                .memberId(new MemberId(UUID.randomUUID()))
                .memberKind(MemberKind.CUSTOMER)
                .memberStatus(MemberStatus.ACTIVATE)
                .phoneNumber("0101231234")
                .name("TestName")
                .password("TestPassword123!@#")
                .email("testEmail@test.com")
                .address(new StreetAddress(UUID.randomUUID(),"123 Street", "99999", "City"))
                .build();

        return new MemberUpdatedEvent(member, ZonedDateTime.now());
    }

}