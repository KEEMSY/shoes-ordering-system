package com.shoes.ordering.system;

import com.shoes.ordering.system.domains.member.domain.core.MemberDomainService;
import com.shoes.ordering.system.domains.member.domain.core.MemberDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public MemberDomainService orderDomainService() {
        return new MemberDomainServiceImpl();
    }
}
