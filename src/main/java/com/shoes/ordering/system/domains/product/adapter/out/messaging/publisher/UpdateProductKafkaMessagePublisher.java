package com.shoes.ordering.system.domains.product.adapter.out.messaging.publisher;

import com.shoes.ordering.system.common.kafka.model.UpdateProductRequestAvroModel;
import com.shoes.ordering.system.common.kafka.producer.service.KafkaProducer;
import com.shoes.ordering.system.domains.product.adapter.out.messaging.mapper.ProductMessagingDataMapper;
import com.shoes.ordering.system.domains.product.domain.application.config.ProductServiceConfigData;
import com.shoes.ordering.system.domains.product.domain.application.ports.output.message.publisher.ProductUpdatedRequestMessagePublisher;
import com.shoes.ordering.system.domains.product.domain.core.event.ProductUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UpdateProductKafkaMessagePublisher implements ProductUpdatedRequestMessagePublisher {

    private final ProductMessagingDataMapper productMessagingDataMapper;
    private final ProductServiceConfigData productServiceConfigData;
    private final KafkaProducer<String, UpdateProductRequestAvroModel> kafkaProducer;
    private final ProductKafkaMessageHelper productKafkaMessageHelper;

    public UpdateProductKafkaMessagePublisher(ProductMessagingDataMapper productMessagingDataMapper,
                                              ProductServiceConfigData productServiceConfigData,
                                              KafkaProducer<String, UpdateProductRequestAvroModel> kafkaProducer,
                                              ProductKafkaMessageHelper productKafkaMessageHelper) {
        this.productMessagingDataMapper = productMessagingDataMapper;
        this.productServiceConfigData = productServiceConfigData;
        this.kafkaProducer = kafkaProducer;
        this.productKafkaMessageHelper = productKafkaMessageHelper;
    }

    @Override
    public void publish(ProductUpdatedEvent domainEvent) {
        String productId = domainEvent.getProduct().getId().getValue().toString();
        log.info("Received ProductUpdatedEvent for productId: {}", productId);

        try {
            UpdateProductRequestAvroModel updateProductRequestAvroModel = productMessagingDataMapper
                    .productUpdatedProductRequestAvroModel(domainEvent);

            kafkaProducer.send(productServiceConfigData.getUpdateProductRequestTopicName(),
                    productId,
                    updateProductRequestAvroModel,
                    productKafkaMessageHelper
                            .getKafkaCallback(productServiceConfigData
                                            .getUpdateProductRequestTopicName(),
                                    updateProductRequestAvroModel,
                                    productId,
                                    "UpdateProductRequestAvroModel")
            );
            log.info("UpdateProductRequestAvroModel sent to Kafka for productId: {}"
                    , updateProductRequestAvroModel.getProductId());
        } catch (Exception e) {
            log.error("Error while sending UpdateProductRequestAvroModel message to Kafka with productId: {}, error: {}"
                    ,productId, e.getMessage() );
        }
    }
}
