package com.noticeflow.back.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "연결 코드 생성 요청")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionCodeRequest {

    @Schema(description = "연결 코드 이름", example = "1학년 1반", required = true)
    @NotBlank(message = "연결 코드 이름을 입력해주세요")
    private String name;
}