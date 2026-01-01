package com.noticeflow.back.service;

import com.noticeflow.back.domain.User;
import com.noticeflow.back.domain.UserRole;
import com.noticeflow.back.domain.UserStatus;
import com.noticeflow.back.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("=== OAuth2 로그인 시작 ===");
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        String picture = (String) attributes.get("picture");
        String providerId = (String) attributes.get("sub");

        log.info("사용자 정보 - email: {}, name: {}, provider: {}", email, name, registrationId);

        User user = userRepository.findByProviderAndProviderId(registrationId, providerId)
                .orElseGet(() -> {
                    log.info("신규 사용자 생성 중...");
                    return createUser(email, name, picture, registrationId, providerId);
                });

        log.info("사용자 조회/생성 완료 - userId: {}, role: {}", user.getId(), user.getRole());
        return new CustomOAuth2User(user, attributes);
    }

    private User createUser(String email, String name, String picture, String provider, String providerId) {
        User user = User.builder()
                .email(email)
                .name(name)
                .profileImage(picture)
                .role(UserRole.ANONYMOUS)  // 가입 미완료 상태
                .status(UserStatus.PENDING)  // 초기 상태: 추가 정보 미입력
                .provider(provider)
                .providerId(providerId)
                .build();

        User savedUser = userRepository.save(user);
        log.info("사용자 DB 저장 완료 - userId: {}, status: {}", savedUser.getId(), savedUser.getStatus());
        return savedUser;
    }
}