package com.noticeflow.back.controller;

import com.noticeflow.back.domain.User;
import com.noticeflow.back.dto.ApiResponse;
import com.noticeflow.back.dto.DashboardCountsResponse;
import com.noticeflow.back.service.DashboardService;
import com.noticeflow.back.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "대시보드 API", description = "대시보드 현황 조회 API")
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;
    private final UserService userService;

    @Operation(
            summary = "대시보드 카운트 조회",
            description = "총 공지 수, 발송된 공지 수, 등록된 사용자 수, 템플릿 수를 조회합니다. 기관 관리자만 사용 가능합니다.",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "성공",
                    content = @Content(schema = @Schema(implementation = DashboardCountsResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "인증 실패"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "403",
                    description = "기관 관리자만 사용 가능"
            )
    })
    @GetMapping("/counts")
    public ResponseEntity<ApiResponse<DashboardCountsResponse>> getCounts(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        User user = userService.getUserEntityById(userId);

        DashboardCountsResponse counts = dashboardService.getCounts(user);
        return ResponseEntity.ok(ApiResponse.success(counts));
    }
}