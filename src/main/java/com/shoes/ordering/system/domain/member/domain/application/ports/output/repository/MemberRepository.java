package com.shoes.ordering.system.domain.member.domain.application.ports.output.repository;

import com.shoes.ordering.system.domain.member.domain.core.entity.Member;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;


@Component
public interface MemberRepository {

    Member save(Member member);

    Optional<Member> findByMemberId(UUID memberId);
}
