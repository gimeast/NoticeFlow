package com.noticeflow.back.controller;

import com.noticeflow.back.domain.User;
import com.noticeflow.back.dto.ApiResponse;
import com.noticeflow.back.dto.NoticeRequest;
import com.noticeflow.back.dto.NoticeResponse;
import com.noticeflow.back.service.NoticeService;
import com.noticeflow.back.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "공지사항 API", description = "공지사항 관리 API")
@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;
    private final UserService userService;

    @Operation(
            summary = "공지사항 작성",
            description = "새로운 공지사항을 작성합니다. 템플릿 선택은 선택사항이며, 내용에 포함된 변수({{제목}}, {{날짜}}, {{기관명}}, {{담당자}})는 자동으로 치환됩니다. 기관 관리자만 사용 가능합니다.",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "성공",
                    content = @Content(schema = @Schema(implementation = NoticeResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 또는 숨겨진 카테고리 사용"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "인증 실패"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "카테고리 또는 템플릿을 찾을 수 없음"
            )
    })
    @PostMapping
    public ResponseEntity<ApiResponse<NoticeResponse>> createNotice(
            Authentication authentication,
            @Valid @RequestBody NoticeRequest request) {
        Long userId = (Long) authentication.getPrincipal();
        User user = userService.getUserEntityById(userId);

        NoticeResponse notice = noticeService.createNotice(user, request);
        return ResponseEntity.ok(ApiResponse.success("공지사항이 작성되었습니다.", notice));
    }

    @Operation(
            summary = "공지사항 목록 조회",
            description = "기관의 공지사항 목록을 페이징으로 조회합니다. 기본값: page=0, size=10, sort=createdAt,desc. includeContent=true로 설정하면 본문 내용도 함께 반환됩니다.",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "성공",
                    content = @Content(schema = @Schema(implementation = NoticeResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "인증 실패"
            )
    })
    @GetMapping
    public ResponseEntity<ApiResponse<Page<NoticeResponse>>> getNotices(
            Authentication authentication,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(defaultValue = "false") boolean includeContent) {
        Long userId = (Long) authentication.getPrincipal();
        User user = userService.getUserEntityById(userId);

        Page<NoticeResponse> notices = noticeService.getNotices(user, pageable, includeContent);
        return ResponseEntity.ok(ApiResponse.success(notices));
    }

    @Operation(
            summary = "공지사항 상세 조회",
            description = "특정 공지사항의 상세 정보를 조회합니다.",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "성공",
                    content = @Content(schema = @Schema(implementation = NoticeResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "인증 실패"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "403",
                    description = "접근 권한 없음"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "공지사항을 찾을 수 없음"
            )
    })
    @GetMapping("/{noticeId}")
    public ResponseEntity<ApiResponse<NoticeResponse>> getNotice(
            Authentication authentication,
            @PathVariable Long noticeId) {
        Long userId = (Long) authentication.getPrincipal();
        User user = userService.getUserEntityById(userId);

        NoticeResponse notice = noticeService.getNotice(user, noticeId);
        return ResponseEntity.ok(ApiResponse.success(notice));
    }
}
