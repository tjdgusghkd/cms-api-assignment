package com.malgn.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.malgn.member.dto.LoginRequest;
import com.malgn.member.dto.SignupRequest;
import com.malgn.member.entity.Member;
import com.malgn.member.enums.Role;
import com.malgn.member.exception.DuplicateLoginIdException;
import com.malgn.member.exception.InvalidLoginException;
import com.malgn.member.exception.PasswordMismatchException;
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
                .orElseThrow(InvalidLoginException::new);

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new InvalidLoginException();
        }

        return member;
    }

    @Transactional
    public Long signup(@Valid SignupRequest request) {
        if (memberRepository.existsByLoginId(request.getLoginId())) {
            throw new DuplicateLoginIdException();
        }

        if (!request.isPasswordMatching()) {
            throw new PasswordMismatchException();
        }

        Member member = new Member(
                request.getLoginId(),
                passwordEncoder.encode(request.getPassword()),
                request.getName(),
                Role.ROLE_USER
        );

        return memberRepository.save(member).getMemberId();
    }
}
