package com.shoes.ordering.system.domain.member.dataaccess.adapter;

import com.shoes.ordering.system.domain.member.dataaccess.mapper.MemberDataAccessMapper;
import com.shoes.ordering.system.domain.member.dataaccess.repository.MemberJpaRepository;
import com.shoes.ordering.system.domain.member.domain.application.ports.output.repository.MemberRepository;
import com.shoes.ordering.system.domain.member.domain.core.entity.Member;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
@Component
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;
    private final MemberDataAccessMapper memberDataAccessMapper;

    public MemberRepositoryImpl(MemberJpaRepository memberJpaRepository,
                                MemberDataAccessMapper memberDataAccessMapper) {
        this.memberJpaRepository = memberJpaRepository;
        this.memberDataAccessMapper = memberDataAccessMapper;
    }

    @Override
    public Member save(Member member) {
        return memberDataAccessMapper.memberEntityToMember(memberJpaRepository
                .save(memberDataAccessMapper.memberToMemberEntity(member)));
    }

    @Override
    public Optional<Member> findByMemberId(UUID memberId) {
        return memberJpaRepository.findByMemberId(memberId)
                .map(memberDataAccessMapper::memberEntityToMember);
    }
}
