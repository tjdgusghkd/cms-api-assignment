package com.malgn.member.dto;

import lombok.Getter;

@Getter
public class SignupResponse {
    private Long memberId;
    private String loginId;
    private String name;
    private String role;

    public SignupResponse(Long memberId, String loginId, String name, String role) {
        this.memberId = memberId;
        this.loginId = loginId;
        this.name = name;
        this.role = role;
    }
}