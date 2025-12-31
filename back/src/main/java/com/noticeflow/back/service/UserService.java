package com.noticeflow.back.service;

import com.noticeflow.back.domain.*;
import com.noticeflow.back.dto.NormalUserRegistrationRequest;
import com.noticeflow.back.dto.OrganizationRegistrationRequest;
import com.noticeflow.back.dto.UserResponse;
import com.noticeflow.back.repository.ConnectionCodeRepository;
import com.noticeflow.back.repository.NormalUserRepository;
import com.noticeflow.back.repository.OrganizationRepository;
import com.noticeflow.back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final NormalUserRepository normalUserRepository;
    private final ConnectionCodeRepository connectionCodeRepository;

    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return UserResponse.from(user);
    }

    @Transactional
    public UserResponse registerAsNormalUser(Long userId, NormalUserRegistrationRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (user.getStatus() == UserStatus.COMPLETED) {
            throw new IllegalStateException("이미 가입이 완료되었습니다.");
        }

        // 연결 코드로 기관 찾기
        ConnectionCode connectionCode = connectionCodeRepository.findByCodeAndIsActiveTrue(request.getConnectionCode())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 연결 코드입니다."));

        user.setStatus(UserStatus.COMPLETED);
        // role은 이미 NORMAL로 설정되어 있음

        // NormalUser 생성
        NormalUser normalUser = NormalUser.builder()
                .user(user)
                .name(request.getName())
                .contactNumber(request.getContactNumber())
                .organization(connectionCode.getOrganization())
                .connectionCode(connectionCode)
                .build();

        normalUserRepository.save(normalUser);
        user.setNormalUser(normalUser);

        return UserResponse.from(user);
    }

    @Transactional
    public UserResponse registerOrganization(Long userId, OrganizationRegistrationRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (user.getStatus() == UserStatus.COMPLETED) {
            throw new IllegalStateException("이미 가입이 완료되었습니다.");
        }

        user.setRole(UserRole.ORGANIZATION);
        user.setStatus(UserStatus.COMPLETED);

        Organization organization = Organization.builder()
                .user(user)
                .type(request.getType())
                .organizationName(request.getOrganizationName())
                .managerName(request.getManagerName())
                .contactNumber(request.getContactNumber())
                .address(request.getAddress())
                .build();

        organizationRepository.save(organization);
        user.setOrganization(organization);

        // 기관 등록 시 자동으로 연결 코드 생성
        ConnectionCode connectionCode = ConnectionCode.builder()
                .organization(organization)
                .name("기본 연결 코드")
                .isActive(true)
                .build();

        connectionCodeRepository.save(connectionCode);

        return UserResponse.from(user);
    }
}