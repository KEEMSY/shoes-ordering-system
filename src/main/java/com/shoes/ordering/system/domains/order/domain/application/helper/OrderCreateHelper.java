package com.shoes.ordering.system.domains.order.domain.application.helper;

import com.shoes.ordering.system.domains.member.domain.application.ports.output.repository.MemberRepository;
import com.shoes.ordering.system.domains.member.domain.core.entity.Member;
import com.shoes.ordering.system.domains.order.domain.application.dto.create.CreateOrderCommand;
import com.shoes.ordering.system.domains.order.domain.application.mapper.OrderDataMapper;
import com.shoes.ordering.system.domains.order.domain.application.ports.output.repository.OrderRepository;
import com.shoes.ordering.system.domains.order.domain.core.OrderDomainService;
import com.shoes.ordering.system.domains.order.domain.core.entity.Order;
import com.shoes.ordering.system.domains.order.domain.core.event.OrderCreatedEvent;
import com.shoes.ordering.system.domains.order.domain.core.exception.OrderDomainException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class OrderCreateHelper {

    private final OrderDomainService orderDomainService;
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final OrderDataMapper orderDataMapper;

    public OrderCreateHelper(OrderDomainService orderDomainService,
                             OrderRepository orderRepository,
                             MemberRepository memberRepository,
                             OrderDataMapper orderDataMapper) {
        this.orderDomainService = orderDomainService;
        this.orderRepository = orderRepository;
        this.memberRepository = memberRepository;
        this.orderDataMapper = orderDataMapper;
    }

    @Transactional
    public OrderCreatedEvent persistOrder(CreateOrderCommand createOrderCommand) {
        checkMember(createOrderCommand.getMemberId());
        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order);
        saveOrder(order);
        log.info("Order is created with id: {}", orderCreatedEvent.getOrder().getId().getValue());
        return orderCreatedEvent;
    }

    private void checkMember(UUID memberId) {
        Optional<Member> member = memberRepository.findByMemberId(memberId);
        if (member.isEmpty()) {
            log.warn("Could not find member with member id: {}", memberId);
            throw new OrderDomainException("Could not find member with member id: " + member);
        }
    }

    private Order saveOrder(Order order) {
        Order orderResult = orderRepository.save(order);
        if (orderResult == null) {
            log.error("Could not save order!");
            throw new OrderDomainException("Could not save order!");
        }
        log.info("Order is saved with id: {}", orderResult.getId().getValue());
        return orderResult;
    }
}
