package com.shoes.ordering.system.domain.member.messaging.publisher;

import com.shoes.ordering.system.common.kafka.model.CreateMemberRequestAvroModel;
import com.shoes.ordering.system.common.kafka.producer.service.KafkaProducer;
import com.shoes.ordering.system.domain.member.domain.application.config.MemberServiceConfigData;
import com.shoes.ordering.system.domain.member.domain.application.ports.output.message.publisher.MemberCreatedRequestMessagePublisher;
import com.shoes.ordering.system.domain.member.domain.core.event.MemberCreatedEvent;
import com.shoes.ordering.system.domain.member.messaging.mapper.MemberMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreateMemberKafkaMessagePublisher implements MemberCreatedRequestMessagePublisher {

    private final MemberMessagingDataMapper memberMessagingDataMapper;
    private final MemberServiceConfigData memberServiceConfigData;
    private  final KafkaProducer<String, CreateMemberRequestAvroModel> kafkaProducer;
    private final MemberKafkaMessageHelper memberKafkaMessageHelper;

    public CreateMemberKafkaMessagePublisher(MemberMessagingDataMapper memberMessagingDataMapper,
                                             MemberServiceConfigData memberServiceConfigData,
                                             KafkaProducer<String, CreateMemberRequestAvroModel> kafkaProducer,
                                             MemberKafkaMessageHelper memberKafkaMessageHelper) {
        this.memberMessagingDataMapper = memberMessagingDataMapper;
        this.memberServiceConfigData = memberServiceConfigData;
        this.kafkaProducer = kafkaProducer;
        this.memberKafkaMessageHelper = memberKafkaMessageHelper;
    }

    @Override
    public void publish(MemberCreatedEvent domainEvent) {
        String memberId = domainEvent.getMember().getId().getValue().toString();
        log.info("Received MemberCreatedEvent for memberId: {}", memberId);

        try {
            CreateMemberRequestAvroModel createMemberRequestAvroModel = memberMessagingDataMapper
                    .memberCreatedEventToCreateMemberRequestAvroModel(domainEvent);

            kafkaProducer.send(memberServiceConfigData.getCreateMemberRequestTopicName(),
                    memberId,
                    createMemberRequestAvroModel,
                    memberKafkaMessageHelper
                            .getKafkaCallback(memberServiceConfigData
                                    .getCreateMemberRequestTopicName(),
                                    createMemberRequestAvroModel,
                                    memberId,
                                    "CreateMemberRequestAvroModel")
                    );
            log.info("CreateMemberRequestAvroModel sent to Kafka for member id: {}"
                    , createMemberRequestAvroModel.getMemberId());
        } catch (Exception e) {
            log.error("Error while sending CreateMemberRequestAvroModel message to Kafka with member id: {}, error: {}"
                    ,memberId, e.getMessage() );
        }
    }
}
