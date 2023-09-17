package com.shoes.ordering.system.domains.product.domain.application.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "product-service")
public class ProductServiceConfigData {
    private String createProductRequestTopicName;
    private String updateProductRequestTopicName;
}
