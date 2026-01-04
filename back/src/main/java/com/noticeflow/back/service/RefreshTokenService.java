package com.noticeflow.back.service;

import com.noticeflow.back.domain.RefreshToken;
import com.noticeflow.back.exception.RefreshTokenExpiredException;
import com.noticeflow.back.repository.RefreshTokenRepository;
import com.noticeflow.back.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${jwt.refresh-expiration}")
    private long refreshTokenExpiration;

    @Transactional
    public void saveRefreshToken(Long userId) {
        String token = jwtTokenProvider.createRefreshToken(userId);
        LocalDateTime expiryDate = LocalDateTime.now().plusSeconds(refreshTokenExpiration / 1000);

        Optional<RefreshToken> existingToken = refreshTokenRepository.findByUserId(userId);

        if (existingToken.isPresent()) {
            existingToken.get().updateToken(token, expiryDate);
        } else {
            RefreshToken refreshToken = RefreshToken.builder()
                    .userId(userId)
                    .token(token)
                    .expiryDate(expiryDate)
                    .build();
            refreshTokenRepository.save(refreshToken);
        }
    }

    @Transactional(readOnly = true)
    public String getRefreshToken(Long userId) {
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId)
                .orElseThrow(() -> new RefreshTokenExpiredException("리프레시 토큰이 존재하지 않습니다."));

        if (refreshToken.isExpired()) {
            throw new RefreshTokenExpiredException("리프레시 토큰이 만료되었습니다.");
        }

        return refreshToken.getToken();
    }

    @Transactional
    public void deleteRefreshToken(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }

    @Transactional(readOnly = true)
    public boolean validateRefreshToken(Long userId) {
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUserId(userId);

        if (refreshToken.isEmpty()) {
            return false;
        }

        if (refreshToken.get().isExpired()) {
            return false;
        }

        return jwtTokenProvider.validateRefreshToken(refreshToken.get().getToken());
    }
}