package com.shoes.ordering.system.domain.member.domain.application.handler;

import com.shoes.ordering.system.domain.member.domain.application.dto.track.TrackMemberQuery;
import com.shoes.ordering.system.domain.member.domain.application.dto.track.TrackMemberResponse;
import com.shoes.ordering.system.domain.member.domain.application.mapper.MemberDataMapper;
import com.shoes.ordering.system.domain.member.domain.application.ports.output.repository.MemberRepository;
import com.shoes.ordering.system.domain.member.domain.core.entity.Member;
import com.shoes.ordering.system.domain.member.domain.core.exception.MemberNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
public class MemberTrackCommandHandler {

    private final MemberDataMapper memberDataMapper;
    private final MemberRepository memberRepository;

    public MemberTrackCommandHandler(MemberDataMapper memberDataMapper, MemberRepository memberRepository) {
        this.memberDataMapper = memberDataMapper;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public TrackMemberResponse trackMember(TrackMemberQuery trackMemberQuery) {
        Optional<Member> memberResult = memberRepository.findByMemberId(trackMemberQuery.getMemberId());

        if (memberResult.isEmpty()) {
            log.warn("Could not find member with memberId: {}", trackMemberQuery.getMemberId());
            throw new MemberNotFoundException("Could not find member with memberId: " + trackMemberQuery.getMemberId());
        }
        return memberDataMapper.MemberToTrackMemberResponse(memberResult.get());
    }
}
