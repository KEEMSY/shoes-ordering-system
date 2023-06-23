package com.shoes.ordering.system.domains.member.domain.application.helper;

import com.shoes.ordering.system.domains.member.domain.application.dto.create.CreateMemberCommand;
import com.shoes.ordering.system.domains.member.domain.application.dto.update.UpdateMemberCommand;
import com.shoes.ordering.system.domains.member.domain.application.mapper.MemberDataMapper;
import com.shoes.ordering.system.domains.member.domain.application.ports.output.repository.MemberRepository;
import com.shoes.ordering.system.domains.member.domain.core.MemberDomainService;
import com.shoes.ordering.system.domains.member.domain.core.entity.Member;
import com.shoes.ordering.system.domains.member.domain.core.event.MemberUpdatedEvent;
import com.shoes.ordering.system.domains.member.domain.core.event.MemberCreatedEvent;
import com.shoes.ordering.system.domains.member.domain.core.exception.MemberDomainException;
import com.shoes.ordering.system.domains.member.domain.core.exception.MemberNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class MemberHelper {

    private final MemberDomainService memberDomainService;
    private final MemberRepository memberRepository;
    private final MemberDataMapper memberDataMapper;

    public MemberHelper(MemberDomainService memberDomainService,
                        MemberRepository memberRepository,
                        MemberDataMapper memberDataMapper) {
        this.memberDomainService = memberDomainService;
        this.memberRepository = memberRepository;
        this.memberDataMapper = memberDataMapper;
    }

    @Transactional
    public MemberCreatedEvent persistMember(CreateMemberCommand createMemberCommand) {
        Member member = memberDataMapper.createMemberCommandToMember(createMemberCommand);

        MemberCreatedEvent memberCreatedEvent = memberDomainService.validateAndInitiateMember(member);

        saveMember(member);
        log.info("Member is created with id: {}", memberCreatedEvent.getMember().getId().getValue());
        return memberCreatedEvent;
    }

    @Transactional
    public MemberUpdatedEvent updateMemberPersist(UpdateMemberCommand updateMemberCommand) {
        checkMember(updateMemberCommand.getMemberId());
        Member member = memberDataMapper.updateMemberCommandToMember(updateMemberCommand);
        MemberUpdatedEvent memberUpdatedEvent = memberDomainService.updateMember(member);

        saveMember(member);
        log.info("Member is updated with id: {}", memberUpdatedEvent.getMember().getId().getValue());
        return memberUpdatedEvent;
    }

    private void checkMember(UUID memberId) {
        Optional<Member> member = memberRepository.findByMemberId(memberId);
        if (member.isEmpty()) {
            log.warn("Could not find member with memberId: {}", memberId);
            throw new MemberNotFoundException("Could not find member with memberId: " + memberId);
        }
    }

    private Member saveMember(Member member) {
        Member memberResult = memberRepository.save(member);
        if (memberResult == null) {
            log.error("Could not save member!");
            throw new MemberDomainException("Could not save member");
        }

        log.info("Member is saved with id: {}", memberResult.getId().getValue());
        return memberResult;
    }
}
