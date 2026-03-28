package com.malgn.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.malgn.member.dto.LoginRequest;
import com.malgn.member.dto.LoginResponse;
import com.malgn.member.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	
	// 로그인
	@PostMapping("/login")
	public ResponseEntity<Void> login(@Valid @RequestBody LoginRequest request, HttpSession session) {
		LoginResponse loginResponse = memberService.login(request);
		
		session.setAttribute("LOGIN_MEMBER", loginResponse);
		
		return ResponseEntity.ok().build();
		
	}
	
	// 로그아웃
	@PostMapping("/logout")
	public ResponseEntity<Void> logout(HttpServletRequest request) {
	    HttpSession session = request.getSession(false); // 세션이 없으면 새로 만들지 마라
	    if (session != null) {
	        session.invalidate(); // 세션 제거
	    }
	    return ResponseEntity.ok().build();
	}
}
