package com.shoes.ordering.system.domain.member.controller.rest;


import com.shoes.ordering.system.domain.member.domain.application.dto.create.CreateMemberCommand;
import com.shoes.ordering.system.domain.member.domain.application.dto.create.CreateMemberResponse;
import com.shoes.ordering.system.domain.member.domain.application.dto.track.TrackMemberQuery;
import com.shoes.ordering.system.domain.member.domain.application.dto.track.TrackMemberResponse;
import com.shoes.ordering.system.domain.member.domain.application.dto.update.UpdateMemberCommand;
import com.shoes.ordering.system.domain.member.domain.application.dto.update.UpdateMemberResponse;
import com.shoes.ordering.system.domain.member.domain.application.ports.input.service.MemberApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@Slf4j
@RestController
@RequestMapping(value = "/members", produces = "application/vnd.API.v1+json")
public class MemberController {

    private final MemberApplicationService memberApplicationService;

    public MemberController(MemberApplicationService memberApplicationService) {
        this.memberApplicationService = memberApplicationService;
    }

    @PostMapping
    public ResponseEntity<CreateMemberResponse> createMember(@RequestBody CreateMemberCommand createMemberCommand) {
        log.info("Creating member");
        CreateMemberResponse createMemberResponse = memberApplicationService.createMember(createMemberCommand);
        log.info("Member created with Id: {}", createMemberResponse.getMemberId());
        return ResponseEntity.ok(createMemberResponse);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<TrackMemberResponse> getMemberByMemberId(@PathVariable UUID memberId){
        TrackMemberResponse trackMemberResponse
                = memberApplicationService.trackMember(TrackMemberQuery.builder().memberId(memberId).build());
        log.info("Return member with memberId: {}", trackMemberResponse.getMemberId());
        return ResponseEntity.ok(trackMemberResponse);
    }

    @PostMapping("/{memberId}")
    public ResponseEntity<UpdateMemberResponse> updateMember(@RequestBody UpdateMemberCommand updateMemberCommand) {
        UpdateMemberResponse updateMemberResponse = memberApplicationService.updateMember(updateMemberCommand);
        log.info("Member updated with Id: {}", updateMemberResponse.getMemberId());
        return ResponseEntity.status(HttpStatus.CREATED).body(updateMemberResponse);
    }
}
