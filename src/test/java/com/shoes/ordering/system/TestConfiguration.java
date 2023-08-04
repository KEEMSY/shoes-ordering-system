package com.shoes.ordering.system;

import com.shoes.ordering.system.domains.member.domain.application.ports.output.repository.MemberRepository;
import com.shoes.ordering.system.domains.product.domain.application.ports.output.repository.ProductRepository;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.shoes.ordering.system")
public class TestConfiguration {

    @Bean
    public MemberRepository memberRepository() { return Mockito.mock(MemberRepository.class); }
    @Bean
    public ProductRepository productRepository() { return Mockito.mock(ProductRepository.class); }

}
