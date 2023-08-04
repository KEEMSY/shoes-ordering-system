package com.shoes.ordering.system;

import com.shoes.ordering.system.domains.member.domain.core.MemberDomainService;
import com.shoes.ordering.system.domains.member.domain.core.MemberDomainServiceImpl;
import com.shoes.ordering.system.domains.product.domain.core.ProductDomainService;
import com.shoes.ordering.system.domains.product.domain.core.ProductDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public MemberDomainService memberDomainService() {
        return new MemberDomainServiceImpl();
    }

    @Bean
    public ProductDomainService productDomainService() { return new ProductDomainServiceImpl(); }
}
