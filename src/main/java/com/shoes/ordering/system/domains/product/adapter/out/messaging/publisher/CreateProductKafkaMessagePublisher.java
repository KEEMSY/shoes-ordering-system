package com.shoes.ordering.system.domains.product.adapter.out.messaging.publisher;

import com.shoes.ordering.system.common.kafka.model.CreateProductRequestAvroModel;
import com.shoes.ordering.system.common.kafka.producer.service.KafkaProducer;
import com.shoes.ordering.system.domains.product.adapter.out.messaging.mapper.ProductMessagingDataMapper;
import com.shoes.ordering.system.domains.product.domain.application.config.ProductServiceConfigData;
import com.shoes.ordering.system.domains.product.domain.application.ports.output.message.publisher.ProductCreatedRequestMessagePublisher;
import com.shoes.ordering.system.domains.product.domain.core.event.ProductCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreateProductKafkaMessagePublisher implements ProductCreatedRequestMessagePublisher {

    private final ProductMessagingDataMapper productMessagingDataMapper;
    private final ProductServiceConfigData productServiceConfigData;
    private final KafkaProducer<String, CreateProductRequestAvroModel> kafkaProducer;
    private final ProductKafkaMessageHelper productKafkaMessageHelper;

    public CreateProductKafkaMessagePublisher(ProductMessagingDataMapper productMessagingDataMapper,
                                              ProductServiceConfigData productServiceConfigData,
                                              KafkaProducer<String, CreateProductRequestAvroModel> kafkaProducer,
                                              ProductKafkaMessageHelper productKafkaMessageHelper) {
        this.productMessagingDataMapper = productMessagingDataMapper;
        this.productServiceConfigData = productServiceConfigData;
        this.kafkaProducer = kafkaProducer;
        this.productKafkaMessageHelper = productKafkaMessageHelper;
    }

    @Override
    public void publish(ProductCreatedEvent domainEvent) {
        String productId = domainEvent.getProduct().getId().getValue().toString();
        log.info("Received ProductCreatedEvent for productId: {}", productId);

        try {
            CreateProductRequestAvroModel createProductRequestAvroModel = productMessagingDataMapper
                    .productCreatedEventToCreateProductRequestAvroModel(domainEvent);

            kafkaProducer.send(productServiceConfigData.getCreateProductRequestTopicName(),
                    productId,
                    createProductRequestAvroModel,
                    productKafkaMessageHelper
                            .getKafkaCallback(productServiceConfigData
                                            .getCreateProductRequestTopicName(),
                                    createProductRequestAvroModel,
                                    productId,
                                    "CreateProductRequestAvroModel")
            );
            log.info("CreateProductRequestAvroModel sent to Kafka for productId: {}"
                    , createProductRequestAvroModel.getProductId());
        } catch (Exception e) {
            log.error("Error while sending CreateProductRequestAvroModel message to Kafka with productId: {}, error: {}"
                    ,productId, e.getMessage() );
        }
    }
}
