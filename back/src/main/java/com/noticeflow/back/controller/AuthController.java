package com.noticeflow.back.controller;

import com.noticeflow.back.dto.ApiResponse;
import com.noticeflow.back.dto.NormalUserRegistrationRequest;
import com.noticeflow.back.dto.OrganizationRegistrationRequest;
import com.noticeflow.back.dto.UserResponse;
import com.noticeflow.back.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "인증 API", description = "사용자 인증 및 회원가입 관련 API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @Operation(
            summary = "현재 로그인한 사용자 정보 조회",
            description = "JWT 토큰을 사용하여 현재 로그인한 사용자의 정보를 조회합니다.",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "성공",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "인증 실패"
            )
    })
    @GetMapping("/user")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        UserResponse user = userService.getUserById(userId);
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    @Operation(
            summary = "기관 사용자 등록",
            description = "기관 사용자로 등록합니다. 기관 유형, 기관명, 담당자명, 연락처, 주소 정보가 필요합니다.",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "성공",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 또는 이미 기관 사용자로 등록됨"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "인증 실패"
            )
    })
    @PostMapping("/organization/register")
    public ResponseEntity<ApiResponse<UserResponse>> registerOrganization(
            Authentication authentication,
            @Valid @RequestBody OrganizationRegistrationRequest request) {

        Long userId = (Long) authentication.getPrincipal();
        UserResponse user = userService.registerOrganization(userId, request);

        return ResponseEntity.ok(ApiResponse.success("기관 사용자로 등록되었습니다.", user));
    }

    @Operation(
            summary = "일반 사용자 등록",
            description = "일반 사용자로 등록합니다. 이름, 연락처, 기관 연결 코드가 필요합니다.",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "성공",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청 또는 이미 일반 사용자로 등록됨 또는 유효하지 않은 연결 코드"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "인증 실패"
            )
    })
    @PostMapping("/normal/register")
    public ResponseEntity<ApiResponse<UserResponse>> registerNormalUser(
            Authentication authentication,
            @Valid @RequestBody NormalUserRegistrationRequest request) {

        Long userId = (Long) authentication.getPrincipal();
        UserResponse user = userService.registerAsNormalUser(userId, request);

        return ResponseEntity.ok(ApiResponse.success("일반 사용자로 등록되었습니다.", user));
    }

    @Operation(
            summary = "로그아웃",
            description = "로그아웃을 수행하고 httpOnly 쿠키에 저장된 토큰을 삭제합니다.",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "로그아웃 성공"
            )
    })
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletResponse response) {
        // Access Token 쿠키 삭제
        Cookie accessTokenCookie = new Cookie("accessToken", null);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(false);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(0);
        response.addCookie(accessTokenCookie);

        // Refresh Token 쿠키 삭제
        Cookie refreshTokenCookie = new Cookie("refreshToken", null);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(false);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(0);
        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok(ApiResponse.success("로그아웃되었습니다.", null));
    }
}