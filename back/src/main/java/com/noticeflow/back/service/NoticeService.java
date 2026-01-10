package com.noticeflow.back.service;

import com.noticeflow.back.domain.*;
import com.noticeflow.back.dto.NoticeRequest;
import com.noticeflow.back.dto.NoticeResponse;
import com.noticeflow.back.repository.CategoryRepository;
import com.noticeflow.back.repository.NoticeRepository;
import com.noticeflow.back.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final CategoryRepository categoryRepository;
    private final TemplateRepository templateRepository;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");

    @Transactional
    public NoticeResponse createNotice(User user, NoticeRequest request) {
        validateOrganizationUser(user);

        Organization organization = user.getOrganization();

        // 카테고리 검증
        Category category = categoryRepository.findByIdAndOrganization(request.getCategoryId(), organization)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다"));

        if (!category.getIsVisible()) {
            throw new IllegalStateException("숨겨진 카테고리는 사용할 수 없습니다");
        }

        // 템플릿 조회 (선택 사항)
        Template template = null;
        if (request.getTemplateId() != null) {
            template = templateRepository.findById(request.getTemplateId())
                    .orElseThrow(() -> new IllegalArgumentException("템플릿을 찾을 수 없습니다"));
        }

        // 변수 치환
        String originalContent = request.getContent();
        String processedContent = replaceVariables(
                originalContent,
                request.getTitle(),
                organization.getOrganizationName(),
                user.getName()
        );

        // 공지사항 생성
        Notice notice = Notice.builder()
                .organization(organization)
                .user(user)
                .category(category)
                .template(template)
                .title(request.getTitle())
                .content(processedContent)
                .originalContent(originalContent)
                .build();

        notice = noticeRepository.save(notice);
        return NoticeResponse.from(notice);
    }

    public Page<NoticeResponse> getNotices(User user, Pageable pageable) {
        Organization organization = user.getOrganization();
        if (organization == null) {
            throw new IllegalStateException("기관 정보가 없습니다");
        }

        Page<Notice> notices = noticeRepository.findByOrganizationOrderByCreatedAtDesc(organization, pageable);
        return notices.map(NoticeResponse::from);
    }

    public NoticeResponse getNotice(User user, Long noticeId) {
        Organization organization = user.getOrganization();
        if (organization == null) {
            throw new IllegalStateException("기관 정보가 없습니다");
        }

        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("공지사항을 찾을 수 없습니다"));

        if (!notice.getOrganization().getId().equals(organization.getId())) {
            throw new IllegalStateException("접근 권한이 없습니다");
        }

        return NoticeResponse.from(notice);
    }

    /**
     * 변수를 실제 값으로 치환
     * 지원 변수: {{제목}}, {{날짜}}, {{기관명}}, {{담당자}}
     */
    private String replaceVariables(String content, String title, String organizationName, String managerName) {
        String currentDate = LocalDateTime.now().format(DATE_FORMATTER);

        return content
                .replace("{{제목}}", title)
                .replace("{{날짜}}", currentDate)
                .replace("{{기관명}}", organizationName)
                .replace("{{담당자}}", managerName);
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