package com.malgn.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.malgn.member.dto.LoginRequest;
import com.malgn.member.dto.SignupRequest;
import com.malgn.member.entity.Member;
import com.malgn.member.security.LoginMemberPrincipal;
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
	
	// 회원가입
	@PostMapping("/signup")
	public ResponseEntity<Long> signup(@Valid @RequestBody SignupRequest request) {
		Long memberId = memberService.signup(request);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(memberId);
	}
	
	// 로그인
	@PostMapping("/login")
	public ResponseEntity<Void> login(@Valid @RequestBody LoginRequest request, HttpSession session) {
		Member member = memberService.login(request);
		
		LoginMemberPrincipal principal = LoginMemberPrincipal.from	(member);
		
		UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        principal,
                        null,
                        principal.getAuthorities()
                );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                context
        );

        return ResponseEntity.ok().build();
    }
	
	// 로그아웃
	@PostMapping("/logout")
	public ResponseEntity<Void> logout(HttpServletRequest request) {
		SecurityContextHolder.clearContext();
		
		HttpSession session = request.getSession(false);
	    if (session != null) {
	        session.invalidate();
	    }
	    return ResponseEntity.ok().build();
	}
}
