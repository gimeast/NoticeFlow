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

    public User getUserEntityById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
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

        user.setRole(UserRole.NORMAL);
        user.setStatus(UserStatus.COMPLETED);
        user.setContactNumber(request.getContactNumber());

        // NormalUser 생성
        NormalUser normalUser = NormalUser.builder()
                .user(user)
                .organization(connectionCode.getOrganization())
                .connectionCode(connectionCode)
                .build();

        normalUserRepository.save(normalUser);
        user.getNormalUsers().add(normalUser);

        return UserResponse.from(user);
    }

    @Transactional
    public UserResponse joinOrganization(Long userId, String connectionCode) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (user.getRole() != UserRole.NORMAL) {
            throw new IllegalStateException("일반 사용자만 기관에 가입할 수 있습니다.");
        }

        if (user.getStatus() != UserStatus.COMPLETED) {
            throw new IllegalStateException("최초 가입을 먼저 완료해주세요.");
        }

        // 연결 코드로 기관 찾기
        ConnectionCode code = connectionCodeRepository.findByCodeAndIsActiveTrue(connectionCode)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 연결 코드입니다."));

        // 이미 가입한 기관인지 확인
        boolean alreadyJoined = user.getNormalUsers().stream()
                .anyMatch(nu -> nu.getOrganization().getId().equals(code.getOrganization().getId()));

        if (alreadyJoined) {
            throw new IllegalStateException("이미 가입한 기관입니다.");
        }

        // 새로운 NormalUser 생성
        NormalUser newNormalUser = NormalUser.builder()
                .user(user)
                .organization(code.getOrganization())
                .connectionCode(code)
                .build();

        normalUserRepository.save(newNormalUser);
        user.getNormalUsers().add(newNormalUser);

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
        user.setContactNumber(request.getContactNumber());

        Organization organization = Organization.builder()
                .user(user)
                .type(request.getType())
                .organizationName(request.getOrganizationName())
                .address(request.getAddress())
                .build();

        organizationRepository.save(organization);
        user.setOrganization(organization);

        // 기관 등록 시 자동으로 연결 코드 생성
        ConnectionCode connectionCode = ConnectionCode.builder()
                .organization(organization)
                .isActive(true)
                .build();

        connectionCodeRepository.save(connectionCode);

        return UserResponse.from(user);
    }
}