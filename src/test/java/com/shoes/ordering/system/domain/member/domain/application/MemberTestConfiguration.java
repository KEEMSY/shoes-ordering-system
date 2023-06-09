package com.shoes.ordering.system.domain.member.domain.application;

import com.shoes.ordering.system.domain.member.domain.application.ports.output.repository.MemberRepository;
import com.shoes.ordering.system.domain.member.domain.core.MemberDomainService;
import com.shoes.ordering.system.domain.member.domain.core.MemberDomainServiceImpl;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.shoes.ordering.system")
public class MemberTestConfiguration {

    @Bean
    public MemberRepository memberRepository() { return Mockito.mock(MemberRepository.class); }

    @Bean
    public MemberDomainService memberDomainService() { return new MemberDomainServiceImpl(); }
}
