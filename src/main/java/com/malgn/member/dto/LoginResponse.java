package com.malgn.member.dto;

import com.malgn.member.entity.Member;
import com.malgn.member.enums.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponse {
	private Long memberId;
	private String loginId;
	private Role role;
	
	public LoginResponse(Member member) {
		this.memberId = member.getMemberId();
		this.loginId = member.getLoginId();
		this.role = member.getRole();
	}
}
