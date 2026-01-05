package com.noticeflow.back.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "추가 기관 가입 요청")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JoinOrganizationRequest {

    @Schema(description = "기관 연결 코드", example = "abc123-def456-ghi789", required = true)
    @NotBlank(message = "기관 연결 코드를 입력해주세요")
    private String connectionCode;
}