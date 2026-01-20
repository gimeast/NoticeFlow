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
        Category[] categories;

        if (existingCategories.isEmpty()) {
            String[] categoryNames = {"일반 공지", "시스템/점검", "보안", "업데이트", "이벤트/혜택", "정책/약관"};
            categories = new Category[categoryNames.length];

            for (int i = 0; i < categoryNames.length; i++) {
                categories[i] = Category.builder()
                        .organization(organization)
                        .name(categoryNames[i])
                        .isVisible(true)
                        .build();
                categories[i] = categoryRepository.save(categories[i]);
                log.info("카테고리 생성: {}", categories[i].getName());
            }
        } else {
            categories = existingCategories.toArray(new Category[0]);
            log.info("기존 카테고리 사용: {}개", categories.length);
        }

        // 공지사항 생성 (이미 있는지 확인)
        List<Notice> existingNotices = noticeRepository.findByOrganizationOrderByCreatedAtDesc(organization);
        if (!existingNotices.isEmpty()) {
            log.info("공지사항이 이미 존재합니다.");
            return;
        }

        // 30개의 테스트 공지사항 생성
        String[] titles = {
                "NoticeFlow 서비스 시작 안내",
                "시스템 정기 점검 안내",
                "보안 업데이트 완료 공지",
                "새로운 기능 업데이트 안내",
                "서비스 이용약관 변경 안내",
                "개인정보처리방침 개정 안내",
                "긴급 서버 점검 공지",
                "연말 휴무 안내",
                "신규 서비스 출시 안내",
                "사용자 매뉴얼 업데이트",
                "모바일 앱 출시 안내",
                "API 버전 업데이트 공지",
                "데이터 백업 완료 안내",
                "네트워크 장애 복구 완료",
                "신규 파트너십 체결 안내",
                "고객 만족도 조사 실시",
                "이벤트 당첨자 발표",
                "서비스 개선 사항 안내",
                "FAQ 업데이트 안내",
                "결제 시스템 점검 안내",
                "회원 등급 정책 변경",
                "포인트 적립 이벤트 안내",
                "고객센터 운영시간 변경",
                "신규 제휴 할인 안내",
                "앱 업데이트 필수 안내",
                "개인정보 수집 동의 갱신",
                "비밀번호 변경 권고",
                "로그인 보안 강화 안내",
                "서비스 안정화 완료 공지",
                "2024년 사업 계획 안내"
        };

        String[] contents = {
                "안녕하세요, {{기관명}}입니다.\n\n{{날짜}}에 NoticeFlow 서비스가 정식 오픈되었습니다.\n\n담당자: {{담당자}}\n\n감사합니다.",
                "안녕하세요, {{기관명}}입니다.\n\n시스템 정기 점검이 예정되어 있습니다.\n\n점검 일시: {{날짜}}\n점검 시간: 02:00 ~ 06:00\n\n이용에 참고 부탁드립니다.",
                "{{기관명}} 보안팀입니다.\n\n{{날짜}} 보안 업데이트가 완료되었습니다.\n\n주요 업데이트 내용:\n- 취약점 패치\n- 인증 로직 강화\n\n담당자: {{담당자}}",
                "안녕하세요, {{기관명}}입니다.\n\n새로운 기능이 추가되었습니다.\n\n- 대시보드 개선\n- 알림 기능 추가\n- 리포트 기능 강화\n\n담당자: {{담당자}}",
                "{{기관명}} 운영팀입니다.\n\n{{날짜}}부터 서비스 이용약관이 변경됩니다.\n\n주요 변경 사항을 확인해 주세요.\n\n담당자: {{담당자}}",
                "안녕하세요, {{기관명}}입니다.\n\n개인정보처리방침이 개정되었습니다.\n\n시행일: {{날짜}}\n\n자세한 내용은 홈페이지를 참고해 주세요.",
                "{{기관명}} 기술팀입니다.\n\n긴급 서버 점검이 진행됩니다.\n\n일시: {{날짜}} 00:00 ~ 02:00\n\n양해 부탁드립니다.",
                "안녕하세요, {{기관명}}입니다.\n\n연말 휴무 일정을 안내드립니다.\n\n휴무 기간: 12월 30일 ~ 1월 2일\n\n담당자: {{담당자}}",
                "{{기관명}}에서 신규 서비스를 출시합니다.\n\n출시일: {{날짜}}\n\n많은 관심 부탁드립니다.\n\n담당자: {{담당자}}",
                "안녕하세요, {{기관명}}입니다.\n\n사용자 매뉴얼이 업데이트되었습니다.\n\n{{날짜}} 기준 최신 버전입니다.\n\n담당자: {{담당자}}",
                "{{기관명}} 모바일팀입니다.\n\n모바일 앱이 정식 출시되었습니다.\n\nApp Store, Play Store에서 다운로드 가능합니다.",
                "{{기관명}} 개발팀입니다.\n\nAPI 버전이 v2.0으로 업데이트됩니다.\n\n적용일: {{날짜}}\n\n기존 API는 3개월간 유지됩니다.",
                "안녕하세요, {{기관명}}입니다.\n\n{{날짜}} 데이터 백업이 완료되었습니다.\n\n정상적으로 처리되었습니다.\n\n담당자: {{담당자}}",
                "{{기관명}} 인프라팀입니다.\n\n{{날짜}} 발생한 네트워크 장애가 복구되었습니다.\n\n불편을 드려 죄송합니다.",
                "안녕하세요, {{기관명}}입니다.\n\n새로운 파트너십이 체결되었습니다.\n\n더 좋은 서비스로 보답하겠습니다.\n\n담당자: {{담당자}}",
                "{{기관명}}입니다.\n\n고객 만족도 조사를 실시합니다.\n\n기간: {{날짜}} ~ 2주간\n\n참여해 주시면 감사하겠습니다.",
                "안녕하세요, {{기관명}}입니다.\n\n이벤트 당첨자를 발표합니다.\n\n발표일: {{날짜}}\n\n당첨자 분들께 개별 연락드리겠습니다.",
                "{{기관명}} 서비스팀입니다.\n\n서비스 개선 사항을 안내드립니다.\n\n- UI/UX 개선\n- 성능 최적화\n- 버그 수정\n\n담당자: {{담당자}}",
                "안녕하세요, {{기관명}}입니다.\n\nFAQ가 업데이트되었습니다.\n\n{{날짜}} 기준 최신 내용입니다.\n\n참고 부탁드립니다.",
                "{{기관명}} 결제팀입니다.\n\n결제 시스템 점검이 예정되어 있습니다.\n\n일시: {{날짜}} 03:00 ~ 05:00\n\n담당자: {{담당자}}",
                "안녕하세요, {{기관명}}입니다.\n\n회원 등급 정책이 변경됩니다.\n\n시행일: {{날짜}}\n\n자세한 내용은 공지사항을 확인해 주세요.",
                "{{기관명}}입니다.\n\n포인트 적립 이벤트를 진행합니다.\n\n기간: {{날짜}} ~ 1개월간\n\n많은 참여 부탁드립니다!",
                "안녕하세요, {{기관명}}입니다.\n\n고객센터 운영시간이 변경됩니다.\n\n변경 후: 평일 09:00 ~ 18:00\n\n시행일: {{날짜}}",
                "{{기관명}}에서 신규 제휴 할인을 안내드립니다.\n\n{{날짜}}부터 적용됩니다.\n\n자세한 내용은 홈페이지를 참고해 주세요.",
                "안녕하세요, {{기관명}}입니다.\n\n앱 업데이트가 필수로 진행됩니다.\n\n업데이트 기한: {{날짜}}\n\n최신 버전으로 업데이트해 주세요.",
                "{{기관명}} 개인정보보호팀입니다.\n\n개인정보 수집 동의 갱신이 필요합니다.\n\n갱신 기한: {{날짜}}\n\n담당자: {{담당자}}",
                "안녕하세요, {{기관명}}입니다.\n\n보안 강화를 위해 비밀번호 변경을 권고드립니다.\n\n3개월 이상 미변경 시 변경해 주세요.",
                "{{기관명}} 보안팀입니다.\n\n로그인 보안이 강화됩니다.\n\n- 2단계 인증 추가\n- 로그인 알림 기능\n\n시행일: {{날짜}}",
                "안녕하세요, {{기관명}}입니다.\n\n서비스 안정화 작업이 완료되었습니다.\n\n{{날짜}} 기준 정상 운영 중입니다.\n\n담당자: {{담당자}}",
                "{{기관명}}입니다.\n\n2024년 사업 계획을 안내드립니다.\n\n더 나은 서비스를 위해 노력하겠습니다.\n\n담당자: {{담당자}}"
        };

        // 각 공지사항에 맞는 카테고리 인덱스 매핑
        // 0: 일반 공지, 1: 시스템/점검, 2: 보안, 3: 업데이트, 4: 이벤트/혜택, 5: 정책/약관
        int[] categoryIndices = {
                0,  // NoticeFlow 서비스 시작 안내 - 일반 공지
                1,  // 시스템 정기 점검 안내 - 시스템/점검
                2,  // 보안 업데이트 완료 공지 - 보안
                3,  // 새로운 기능 업데이트 안내 - 업데이트
                5,  // 서비스 이용약관 변경 안내 - 정책/약관
                5,  // 개인정보처리방침 개정 안내 - 정책/약관
                1,  // 긴급 서버 점검 공지 - 시스템/점검
                0,  // 연말 휴무 안내 - 일반 공지
                0,  // 신규 서비스 출시 안내 - 일반 공지
                3,  // 사용자 매뉴얼 업데이트 - 업데이트
                3,  // 모바일 앱 출시 안내 - 업데이트
                3,  // API 버전 업데이트 공지 - 업데이트
                1,  // 데이터 백업 완료 안내 - 시스템/점검
                1,  // 네트워크 장애 복구 완료 - 시스템/점검
                0,  // 신규 파트너십 체결 안내 - 일반 공지
                4,  // 고객 만족도 조사 실시 - 이벤트/혜택
                4,  // 이벤트 당첨자 발표 - 이벤트/혜택
                3,  // 서비스 개선 사항 안내 - 업데이트
                3,  // FAQ 업데이트 안내 - 업데이트
                1,  // 결제 시스템 점검 안내 - 시스템/점검
                5,  // 회원 등급 정책 변경 - 정책/약관
                4,  // 포인트 적립 이벤트 안내 - 이벤트/혜택
                0,  // 고객센터 운영시간 변경 - 일반 공지
                4,  // 신규 제휴 할인 안내 - 이벤트/혜택
                3,  // 앱 업데이트 필수 안내 - 업데이트
                5,  // 개인정보 수집 동의 갱신 - 정책/약관
                2,  // 비밀번호 변경 권고 - 보안
                2,  // 로그인 보안 강화 안내 - 보안
                1,  // 서비스 안정화 완료 공지 - 시스템/점검
                0   // 2024년 사업 계획 안내 - 일반 공지
        };

        for (int i = 0; i < 30; i++) {
            String title = titles[i];
            String originalContent = contents[i];

            // 카테고리 배열 길이에 맞게 인덱스 조정
            int categoryIndex = categoryIndices[i] % categories.length;
            Category selectedCategory = categories[categoryIndex];

            String processedContent = replaceVariables(
                    originalContent,
                    title,
                    organization.getOrganizationName(),
                    user.getName()
            );

            Notice notice = Notice.builder()
                    .organization(organization)
                    .user(user)
                    .category(selectedCategory)
                    .template(defaultTemplate)
                    .title(title)
                    .content(processedContent)
                    .originalContent(originalContent)
                    .build();

            noticeRepository.save(notice);
        }

        log.info("30개의 더미 공지사항이 6개 카테고리에 골고루 생성되었습니다.");
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