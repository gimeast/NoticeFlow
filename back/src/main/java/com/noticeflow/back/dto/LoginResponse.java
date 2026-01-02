package com.noticeflow.back.dto;

import com.noticeflow.back.domain.User;
import com.noticeflow.back.domain.UserRole;
import com.noticeflow.back.domain.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "로그인 응답")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

    @Schema(description = "액세스 토큰")
    private String accessToken;

    @Schema(description = "이메일", example = "user@example.com")
    private String email;

    @Schema(description = "이름", example = "홍길동")
    private String name;

    @Schema(description = "사용자 역할", example = "NORMAL")
    private UserRole role;

    @Schema(description = "사용자 상태", example = "ACTIVE")
    private UserStatus status;

    public static LoginResponse from(User user, String accessToken) {
        return LoginResponse.builder()
                .accessToken(accessToken)
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .status(user.getStatus())
                .build();
    }
}