package com.noticeflow.back.controller;

import com.noticeflow.back.domain.User;
import com.noticeflow.back.dto.ApiResponse;
import com.noticeflow.back.dto.TemplateResponse;
import com.noticeflow.back.service.TemplateService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "템플릿 API", description = "공지사항 템플릿 관련 API")
@RestController
@RequestMapping("/api/templates")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;
    private final UserService userService;

    @Operation(
            summary = "템플릿 목록 조회",
            description = "사용 가능한 모든 템플릿을 조회합니다. 기관 관리자만 사용 가능합니다.",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "성공",
                    content = @Content(schema = @Schema(implementation = TemplateResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "인증 실패"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "403",
                    description = "권한 없음 - 기관 관리자만 접근 가능"
            )
    })
    @GetMapping
    public ResponseEntity<ApiResponse<List<TemplateResponse>>> getAllTemplates(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        User user = userService.getUserEntityById(userId);

        List<TemplateResponse> templates = templateService.getAllTemplates(user);
        return ResponseEntity.ok(ApiResponse.success(templates));
    }

    @Operation(
            summary = "템플릿 상세 조회",
            description = "특정 템플릿의 상세 정보를 조회합니다. 기관 관리자만 사용 가능합니다.",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "성공",
                    content = @Content(schema = @Schema(implementation = TemplateResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "인증 실패"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "403",
                    description = "권한 없음 - 기관 관리자만 접근 가능"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "템플릿을 찾을 수 없음"
            )
    })
    @GetMapping("/{templateId}")
    public ResponseEntity<ApiResponse<TemplateResponse>> getTemplate(
            Authentication authentication,
            @PathVariable Long templateId) {
        Long userId = (Long) authentication.getPrincipal();
        User user = userService.getUserEntityById(userId);

        TemplateResponse template = templateService.getTemplate(user, templateId);
        return ResponseEntity.ok(ApiResponse.success(template));
    }
}