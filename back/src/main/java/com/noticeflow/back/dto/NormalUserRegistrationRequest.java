package com.noticeflow.back.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "일반 사용자 등록 요청")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NormalUserRegistrationRequest {

    @Schema(description = "이름", example = "홍길동", required = true)
    @NotBlank(message = "이름을 입력해주세요")
    private String name;

    @Schema(description = "연락처", example = "01012345678", required = true)
    @NotBlank(message = "연락처를 입력해주세요")
    private String contactNumber;

    @Schema(description = "기관 연결 코드", example = "550e8400-e29b-41d4-a716-446655440000", required = true)
    @NotBlank(message = "기관 연결 코드를 입력해주세요")
    private String connectionCode;
}