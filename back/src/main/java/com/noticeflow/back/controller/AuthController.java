package com.noticeflow.back.controller;

import com.noticeflow.back.dto.*;
import com.noticeflow.back.exception.RefreshTokenExpiredException;
import com.noticeflow.back.security.JwtTokenProvider;
import com.noticeflow.back.service.RefreshTokenService;
import com.noticeflow.back.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "인증 API", description = "사용자 인증 및 회원가입 관련 API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    @Value("${jwt.refresh-expiration}")
    private long refreshTokenExpiration;

    @Value("${cookie.secure}")
    private boolean cookieSecure;

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
            summary = "토큰 갱신",
            description = "백엔드에서 관리하는 리프레시 토큰을 사용하여 새로운 액세스 토큰을 발급받습니다.",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "토큰 갱신 성공"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "리프레시 토큰 만료 또는 유효하지 않음"
            )
    })
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 쿠키에서 액세스 토큰 가져오기 (만료된 토큰도 가능)
            String accessToken = getTokenFromCookie(request);

            if (accessToken == null) {
                return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)
                        .body(new ErrorResponse("UNAUTHORIZED", "액세스 토큰이 없습니다."));
            }

            // 만료된 토큰에서도 userId 추출
            Long userId = jwtTokenProvider.getUserIdFromExpiredToken(accessToken);

            // DB에서 리프레시 토큰 조회 및 검증
            if (!refreshTokenService.validateRefreshToken(userId)) {
                return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)
                        .body(new ErrorResponse("UNAUTHORIZED", "유효하지 않은 리프레시 토큰입니다.", "REFRESH_TOKEN"));
            }

            // RTR: 새로운 리프레시 토큰 발급 (기존 만료 시간 유지)
            refreshTokenService.rotateRefreshToken(userId);

            // 새로운 액세스 토큰 생성
            UserResponse user = userService.getUserById(userId);
            String newAccessToken = jwtTokenProvider.createAccessToken(userId, user.getEmail());

            // 새로운 액세스 토큰을 쿠키에 설정
            Cookie accessTokenCookie = new Cookie("accessToken", newAccessToken);
            accessTokenCookie.setHttpOnly(true);
            accessTokenCookie.setSecure(cookieSecure);
            accessTokenCookie.setPath("/");
            accessTokenCookie.setMaxAge((int) (refreshTokenExpiration / 1000)); // 리프레시 토큰 만료 시간과 동일
            response.addCookie(accessTokenCookie);

            return ResponseEntity.ok(ApiResponse.success("토큰이 갱신되었습니다.", null));

        } catch (RefreshTokenExpiredException e) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)
                    .body(new ErrorResponse("UNAUTHORIZED", e.getMessage(), e.getTokenType()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)
                    .body(new ErrorResponse("UNAUTHORIZED", "토큰 갱신에 실패했습니다."));
        }
    }

    private String getTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    @Operation(
            summary = "로그아웃",
            description = "로그아웃을 수행하고 DB의 리프레시 토큰과 쿠키의 액세스 토큰을 삭제합니다.",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "로그아웃 성공"
            )
    })
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(Authentication authentication, HttpServletResponse response) {
        // DB에서 Refresh Token 삭제
        Long userId = (Long) authentication.getPrincipal();
        refreshTokenService.deleteRefreshToken(userId);

        // Access Token 쿠키 삭제
        Cookie accessTokenCookie = new Cookie("accessToken", null);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(cookieSecure);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(0);
        response.addCookie(accessTokenCookie);

        return ResponseEntity.ok(ApiResponse.success("로그아웃되었습니다.", null));
    }
}