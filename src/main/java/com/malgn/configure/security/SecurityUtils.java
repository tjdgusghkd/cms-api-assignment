package com.malgn.configure.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.malgn.member.security.LoginMemberPrincipal;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static LoginMemberPrincipal getLoginMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof LoginMemberPrincipal principal)) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        return principal;
    }
}