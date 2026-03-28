package com.malgn.member.security;

  import java.io.Serializable;
  import java.util.Collection;
  import java.util.List;

  import org.springframework.security.core.GrantedAuthority;
  import org.springframework.security.core.authority.SimpleGrantedAuthority;

  import com.malgn.member.entity.Member;
  import com.malgn.member.enums.Role;

  public class LoginMemberPrincipal implements Serializable {
      private final Long memberId;
      private final String loginId;
      private final Role role;

      public LoginMemberPrincipal(Long memberId, String loginId, Role role) {
          this.memberId = memberId;
          this.loginId = loginId;
          this.role = role;
      }

      public static LoginMemberPrincipal from(Member member) {
          return new LoginMemberPrincipal(
                  member.getMemberId(),
                  member.getLoginId(),
                  member.getRole()
          );
      }

      public Long getMemberId() {
          return memberId;
      }

      public String getLoginId() {
          return loginId;
      }

      public Role getRole() {
          return role;
      }

      public Collection<? extends GrantedAuthority> getAuthorities() {
          return List.of(new SimpleGrantedAuthority(role.name()));
      }
  }