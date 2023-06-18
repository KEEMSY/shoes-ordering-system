package com.shoes.ordering.system.domain.member.domain.application.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "member-service")
public class MemberServiceConfigData {

    private String createMemberRequestTopicName;
    private String updateMemberRequestTopicName;
}
