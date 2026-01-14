package com.noticeflow.back.service;

import com.noticeflow.back.domain.Organization;
import com.noticeflow.back.domain.User;
import com.noticeflow.back.domain.UserRole;
import com.noticeflow.back.dto.DashboardCountsResponse;
import com.noticeflow.back.repository.EmailSendLogRepository;
import com.noticeflow.back.repository.NormalUserRepository;
import com.noticeflow.back.repository.NoticeRepository;
import com.noticeflow.back.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardService {

    private final NoticeRepository noticeRepository;
    private final EmailSendLogRepository emailSendLogRepository;
    private final NormalUserRepository normalUserRepository;
    private final TemplateRepository templateRepository;

    public DashboardCountsResponse getCounts(User user) {
        validateOrganizationUser(user);

        Organization organization = user.getOrganization();

        Long totalNotices = noticeRepository.countByOrganization(organization);
        Long sentNotices = emailSendLogRepository.countDistinctSentNoticesByOrganization(organization);
        Long registeredUsers = normalUserRepository.countByOrganization(organization);
        Long templates = templateRepository.countByIsDefaultTrueOrOrganization(organization);

        return DashboardCountsResponse.of(totalNotices, sentNotices, registeredUsers, templates);
    }

    private void validateOrganizationUser(User user) {
        if (user.getRole() != UserRole.ORGANIZATION) {
            throw new IllegalStateException("기관 관리자만 사용할 수 있습니다");
        }
        if (user.getOrganization() == null) {
            throw new IllegalStateException("기관 정보가 없습니다");
        }
    }
}