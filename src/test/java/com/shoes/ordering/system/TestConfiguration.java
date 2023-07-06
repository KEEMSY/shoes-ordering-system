package com.shoes.ordering.system;

import com.shoes.ordering.system.domains.member.domain.application.ports.output.repository.MemberRepository;
import com.shoes.ordering.system.domains.member.domain.core.MemberDomainService;
import com.shoes.ordering.system.domains.member.domain.core.MemberDomainServiceImpl;
import com.shoes.ordering.system.domains.product.domain.application.ports.output.repository.ProductRepository;
import com.shoes.ordering.system.domains.product.domain.core.ProductDomainService;
import com.shoes.ordering.system.domains.product.domain.core.ProductDomainServiceImpl;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.shoes.ordering.system")
public class TestConfiguration {

    @Bean
    public MemberRepository memberRepository() { return Mockito.mock(MemberRepository.class); }

    @Bean
    public MemberDomainService memberDomainService() { return new MemberDomainServiceImpl(); }

    @Bean
    public ProductRepository productRepository() { return Mockito.mock(ProductRepository.class); }

    @Bean
    public ProductDomainService productDomainService() { return new ProductDomainServiceImpl(); }
}
