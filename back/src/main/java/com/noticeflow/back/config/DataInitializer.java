package com.noticeflow.back.config;

import com.noticeflow.back.domain.*;
import com.noticeflow.back.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final TemplateRepository templateRepository;
    private final OrganizationRepository organizationRepository;
    private final CategoryRepository categoryRepository;
    private final NoticeRepository noticeRepository;

    @Override
    public void run(String... args) {
        initializeDefaultTemplate();
        initializeDummyData();
    }

    private void initializeDefaultTemplate() {
        // 기본 템플릿이 이미 존재하는지 확인
        if (templateRepository.findByIsDefaultTrue().isPresent()) {
            log.info("기본 템플릿이 이미 존재합니다.");
            return;
        }

        // 기본 템플릿 생성
        Template defaultTemplate = Template.builder()
                .name("기본 템플릿")
                .description("빈 템플릿")
                .content("")
                .isDefault(true)
                .build();

        templateRepository.save(defaultTemplate);
        log.info("기본 템플릿이 생성되었습니다.");
    }

    private void initializeDummyData() {
        // 첫 번째 Organization 조회
        List<Organization> organizations = organizationRepository.findAll();
        if (organizations.isEmpty()) {
            log.info("Organization이 없어 더미 데이터를 생성하지 않습니다.");
            return;
        }

        Organization organization = organizations.get(0);
        User user = organization.getUser();
        log.info("Organization 발견: {}", organization.getOrganizationName());

        // 기본 템플릿 조회
        Optional<Template> defaultTemplateOpt = templateRepository.findByIsDefaultTrue();
        if (defaultTemplateOpt.isEmpty()) {
            log.warn("기본 템플릿을 찾을 수 없어 더미 데이터를 생성하지 않습니다.");
            return;
        }
        Template defaultTemplate = defaultTemplateOpt.get();

        // 카테고리 생성 (이미 있는지 확인)
        List<Category> existingCategories = categoryRepository.findByOrganizationOrderByCreatedAtDesc(organization);
        Category category;
        if (existingCategories.isEmpty()) {
            category = Category.builder()
                    .organization(organization)
                    .name("일반 공지")
                    .isVisible(true)
                    .build();
            category = categoryRepository.save(category);
            log.info("카테고리 생성: {}", category.getName());
        } else {
            category = existingCategories.get(0);
            log.info("기존 카테고리 사용: {}", category.getName());
        }

        // 공지사항 생성 (이미 있는지 확인)
        List<Notice> existingNotices = noticeRepository.findByOrganizationOrderByCreatedAtDesc(organization);
        if (!existingNotices.isEmpty()) {
            log.info("공지사항이 이미 존재합니다.");
            return;
        }

        // 변수를 포함한 공지사항 내용
        String title = "NoticeFlow 서비스 시작 안내";
        String originalContent = "안녕하세요, {{기관명}}입니다.\n\n" +
                "{{날짜}}에 다음과 같이 공지사항을 안내드립니다.\n\n" +
                "제목: {{제목}}\n" +
                "담당자: {{담당자}}\n\n" +
                "문의사항이 있으시면 담당자에게 연락 주시기 바랍니다.\n\n" +
                "감사합니다.";

        // 변수 치환
        String processedContent = replaceVariables(
                originalContent,
                title,
                organization.getOrganizationName(),
                user.getName()
        );

        Notice notice = Notice.builder()
                .organization(organization)
                .user(user)
                .category(category)
                .template(defaultTemplate)
                .title(title)
                .content(processedContent)
                .originalContent(originalContent)
                .build();

        noticeRepository.save(notice);
        log.info("더미 공지사항이 생성되었습니다: {}", notice.getTitle());
    }

    private String replaceVariables(String content, String title, String organizationName, String managerName) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        String currentDate = LocalDateTime.now().format(formatter);

        return content
                .replace("{{제목}}", title)
                .replace("{{날짜}}", currentDate)
                .replace("{{기관명}}", organizationName)
                .replace("{{담당자}}", managerName);
    }
}