package com.shoes.ordering.system.domains.member.controller.rest;

import com.shoes.ordering.system.TestConfiguration;
import com.shoes.ordering.system.domains.member.domain.application.dto.track.TrackMemberQuery;
import com.shoes.ordering.system.domains.member.domain.application.dto.track.TrackMemberResponse;
import com.shoes.ordering.system.domains.member.domain.application.ports.input.service.MemberApplicationService;
import com.shoes.ordering.system.domains.member.domain.core.exception.MemberNotFoundException;
import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TestConfiguration.class)
@AutoConfigureMockMvc
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MemberApplicationService memberApplicationService;

    @Test
    @DisplayName("정상 Member 조회 확인")
    void getMemberByMemberIdTest() throws Exception {
        // given
        UUID targetMemberId = UUID.randomUUID();
        TrackMemberResponse expectedResponse = TrackMemberResponse.builder()
                .memberId(targetMemberId)
                .memberStatus(MemberStatus.ACTIVATE)
                .messages(null)
                .build();

        // Stub: memberApplication.trackMember()
        when(memberApplicationService.trackMember(any(TrackMemberQuery.class))).thenReturn(expectedResponse);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/members/{memberId}", targetMemberId)
                .header("Content-Type", "application/json"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.memberStatus").value("ACTIVATE"));
    }

    @Test
    @DisplayName("존재하지 않는 Member 를 조회 할 경우 에러 확인")
    void getMemberByMemberId_MemberNotFoundException() throws Exception {
        // given
        UUID unknownMemberId = UUID.randomUUID();
        String errorMessage = "Could not find product with memberId: " + unknownMemberId;

        // Stub: productService.trackProduct()
        when(memberApplicationService.trackMember(any(TrackMemberQuery.class)))
                .thenThrow(new MemberNotFoundException(errorMessage));

        // when, then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/members/{memberId}", unknownMemberId)
                .header("Content-Type", "application/json"))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(errorMessage));
    }

}
