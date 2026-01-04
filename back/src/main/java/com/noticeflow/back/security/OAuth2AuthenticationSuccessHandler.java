package com.noticeflow.back.security;

import com.noticeflow.back.service.CustomOAuth2User;
import com.noticeflow.back.service.RefreshTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    @Value("${app.oauth2.redirect-uri}")
    private String redirectUri;

    @Value("${jwt.refresh-expiration}")
    private long refreshTokenExpiration;

    @Value("${cookie.secure}")
    private boolean cookieSecure;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        Long userId = oAuth2User.getUser().getId();

        String accessToken = jwtTokenProvider.createAccessToken(
                userId,
                oAuth2User.getUser().getEmail()
        );

        // Refresh Token을 DB에 저장 (백엔드에서만 관리)
        refreshTokenService.saveRefreshToken(userId);

        // Access Token만 httpOnly 쿠키로 설정
        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(cookieSecure);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge((int) (refreshTokenExpiration / 1000)); // 리프레시 토큰 만료 시간과 동일
        response.addCookie(accessTokenCookie);

        // 항상 동일한 URL로 리다이렉트 (프론트엔드에서 분기 처리)
        getRedirectStrategy().sendRedirect(request, response, redirectUri);
    }
}