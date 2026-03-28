package com.malgn.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter // 수동 매핑이나 테스트 시 편의를 위해 Setter 추가 (선택사항)
@NoArgsConstructor
public class SignupRequest {

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    @Size(min = 4, max = 20, message = "아이디는 4~20자 사이여야 합니다.")
    private String loginId;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,16}$",
        message = "비밀번호는 8~16자 영문, 숫자, 특수문자를 포함해야 합니다."
    )
    private String password;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    // 비밀번호 확인 (프론트엔드와 맞추기 위한 필드)
    private String passwordConfirm;


    public boolean isPasswordMatching() {
        return this.password != null && this.password.equals(this.passwordConfirm);
    }
}