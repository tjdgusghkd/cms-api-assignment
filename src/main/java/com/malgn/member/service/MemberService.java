package com.malgn.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.malgn.member.dto.LoginRequest;
import com.malgn.member.dto.LoginResponse;
import com.malgn.member.entity.Member;
import com.malgn.member.repository.MemberRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	

	  public Member login(@Valid LoginRequest request) {
	      Member member = memberRepository.findByLoginId(request.getLoginId())
	              .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 틀렸습니다."));

	      if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
	          throw new IllegalArgumentException("아이디 또는 비밀번호가 틀렸습니다.");
	      }

	      return member;
	  }

}
