package com.shoes.ordering.system.domains.member.messaging.publisher;

import com.shoes.ordering.system.common.kafka.model.UpdateMemberRequestAvroModel;
import com.shoes.ordering.system.common.kafka.producer.service.KafkaProducer;
import com.shoes.ordering.system.domains.member.domain.application.config.MemberServiceConfigData;
import com.shoes.ordering.system.domains.member.domain.application.ports.output.message.publisher.MemberUpdateRequestMessagePublisher;
import com.shoes.ordering.system.domains.member.domain.core.event.MemberUpdatedEvent;
import com.shoes.ordering.system.domains.member.messaging.mapper.MemberMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UpdateMemberKafkaMessagePublisher implements MemberUpdateRequestMessagePublisher {

    private final MemberMessagingDataMapper memberMessagingDataMapper;
    private final MemberServiceConfigData memberServiceConfigData;
    private  final KafkaProducer<String, UpdateMemberRequestAvroModel> kafkaProducer;
    private final MemberKafkaMessageHelper memberKafkaMessageHelper;

    public UpdateMemberKafkaMessagePublisher(MemberMessagingDataMapper memberMessagingDataMapper,
                                             MemberServiceConfigData memberServiceConfigData,
                                             KafkaProducer<String, UpdateMemberRequestAvroModel> kafkaProducer,
                                             MemberKafkaMessageHelper memberKafkaMessageHelper) {
        this.memberMessagingDataMapper = memberMessagingDataMapper;
        this.memberServiceConfigData = memberServiceConfigData;
        this.kafkaProducer = kafkaProducer;
        this.memberKafkaMessageHelper = memberKafkaMessageHelper;
    }

    @Override
    public void publish(MemberUpdatedEvent domainEvent) {
        String memberId = domainEvent.getMember().getId().getValue().toString();
        log.info("Received MemberUpdatedEvent for memberId: {}", memberId);

        try {
            UpdateMemberRequestAvroModel createMemberRequestAvroModel = memberMessagingDataMapper
                    .memberUpdatedEventToUpdateMemberRequestAvroModel(domainEvent);

            kafkaProducer.send(memberServiceConfigData.getUpdateMemberRequestTopicName(),
                    memberId,
                    createMemberRequestAvroModel,
                    memberKafkaMessageHelper.getKafkaCallback(memberServiceConfigData
                            .getUpdateMemberRequestTopicName(),
                            createMemberRequestAvroModel,
                            memberId,
                            "UpdateMemberRequestAvroModel")
            );
            log.info("CreateMemberRequestAvroModel sent to Kafka for member id: {}"
                    , createMemberRequestAvroModel.getMemberId());
        } catch (Exception e) {
            log.error("Error while sending CreateMemberRequestAvroModel message to Kafka with member id: {}, error: {}"
                    ,memberId, e.getMessage() );
        }
    }
}
