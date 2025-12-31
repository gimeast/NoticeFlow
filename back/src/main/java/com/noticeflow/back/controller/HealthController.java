package com.noticeflow.back.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "헬스체크 API", description = "서버 상태 확인 API")
@RestController
public class HealthController {

    @Operation(summary = "서버 상태 확인", description = "API 서버의 정상 작동 여부를 확인합니다.")
    @GetMapping("/")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "message", "NoticeFlow API is running"
        ));
    }
}
