package com.shoes.ordering.system;

import com.shoes.ordering.system.domains.member.domain.application.ports.output.repository.MemberRepository;
import com.shoes.ordering.system.domains.order.domain.application.ports.output.message.publisher.payment.OrderCancelledPaymentRequestMessagePublisher;
import com.shoes.ordering.system.domains.order.domain.application.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import com.shoes.ordering.system.domains.order.domain.application.ports.output.repository.OrderRepository;
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
    @Bean
    public OrderRepository orderRepository() { return Mockito.mock(OrderRepository.class); }
    @Bean
    public OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher() {
        return Mockito.mock(OrderCreatedPaymentRequestMessagePublisher.class);
    }
    @Bean
    public OrderCancelledPaymentRequestMessagePublisher orderCancelledPaymentRequestMessagePublisher() {
        return Mockito.mock(OrderCancelledPaymentRequestMessagePublisher.class);
    }
}
