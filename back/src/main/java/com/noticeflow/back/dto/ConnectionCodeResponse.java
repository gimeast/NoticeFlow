package com.noticeflow.back.dto;

import com.noticeflow.back.domain.ConnectionCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "연결 코드 응답")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConnectionCodeResponse {

    @Schema(description = "연결 코드 ID", example = "1")
    private Long id;

    @Schema(description = "연결 코드", example = "550e8400-e29b-41d4-a716-446655440000")
    private String code;

    @Schema(description = "연결 코드 이름", example = "1학년 1반")
    private String name;

    @Schema(description = "활성화 여부", example = "true")
    private Boolean isActive;

    @Schema(description = "생성 일시")
    private LocalDateTime createdAt;

    public static ConnectionCodeResponse from(ConnectionCode connectionCode) {
        return ConnectionCodeResponse.builder()
                .id(connectionCode.getId())
                .code(connectionCode.getCode())
                .name(connectionCode.getName())
                .isActive(connectionCode.getIsActive())
                .createdAt(connectionCode.getCreatedAt())
                .build();
    }
}