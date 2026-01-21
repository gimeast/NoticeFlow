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
            String[] categoryNames = {"일반 공지", "긴급/점검", "이벤트", "업데이트"};
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

        // 50개의 테스트 공지사항 생성
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
                "2024년 사업 계획 안내",
                "클라우드 마이그레이션 완료",
                "신규 결제 수단 추가 안내",
                "서버 증설 작업 완료",
                "봄맞이 할인 이벤트",
                "회원 가입 이벤트 연장",
                "데이터 보존 정책 변경",
                "새로운 알림 기능 안내",
                "주말 긴급 점검 공지",
                "파일 업로드 용량 확대",
                "다국어 지원 안내",
                "추석 연휴 운영 안내",
                "보안 인증서 갱신 완료",
                "신규 대시보드 출시",
                "사용량 리포트 기능 추가",
                "여름 특별 이벤트 안내",
                "API 호출 제한 정책 변경",
                "긴급 보안 패치 적용",
                "베타 테스트 참여자 모집",
                "서비스 1주년 기념 이벤트",
                "시스템 업그레이드 예정 안내"
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
                "{{기관명}}입니다.\n\n2024년 사업 계획을 안내드립니다.\n\n더 나은 서비스를 위해 노력하겠습니다.\n\n담당자: {{담당자}}",
                "{{기관명}} 인프라팀입니다.\n\n클라우드 마이그레이션이 완료되었습니다.\n\n이제 더 빠르고 안정적인 서비스를 제공해 드립니다.\n\n담당자: {{담당자}}",
                "안녕하세요, {{기관명}}입니다.\n\n신규 결제 수단이 추가되었습니다.\n\n- 카카오페이\n- 네이버페이\n- 토스\n\n{{날짜}}부터 이용 가능합니다.",
                "{{기관명}} 기술팀입니다.\n\n서버 증설 작업이 완료되었습니다.\n\n서비스 안정성이 향상되었습니다.\n\n담당자: {{담당자}}",
                "{{기관명}}입니다.\n\n봄맞이 할인 이벤트를 진행합니다!\n\n기간: {{날짜}} ~ 2주간\n할인율: 최대 30%\n\n많은 참여 부탁드립니다.",
                "안녕하세요, {{기관명}}입니다.\n\n회원 가입 이벤트가 연장되었습니다.\n\n연장 기간: {{날짜}}까지\n\n신규 가입 시 포인트 2배 적립!",
                "{{기관명}} 운영팀입니다.\n\n데이터 보존 정책이 변경됩니다.\n\n시행일: {{날짜}}\n\n자세한 내용은 이용약관을 확인해 주세요.",
                "안녕하세요, {{기관명}}입니다.\n\n새로운 알림 기능이 추가되었습니다.\n\n- 이메일 알림\n- 푸시 알림\n- SMS 알림\n\n설정에서 변경 가능합니다.",
                "{{기관명}} 기술팀입니다.\n\n주말 긴급 점검이 진행됩니다.\n\n일시: {{날짜}} 토요일 02:00 ~ 06:00\n\n양해 부탁드립니다.",
                "안녕하세요, {{기관명}}입니다.\n\n파일 업로드 용량이 확대되었습니다.\n\n기존: 10MB → 변경: 50MB\n\n{{날짜}}부터 적용됩니다.",
                "{{기관명}}입니다.\n\n다국어 지원이 시작됩니다.\n\n지원 언어: 한국어, 영어, 일본어, 중국어\n\n{{날짜}}부터 이용 가능합니다.",
                "안녕하세요, {{기관명}}입니다.\n\n추석 연휴 운영 안내입니다.\n\n휴무 기간: 9월 15일 ~ 9월 18일\n\n긴급 문의: support@noticeflow.com",
                "{{기관명}} 보안팀입니다.\n\n보안 인증서가 갱신되었습니다.\n\n갱신일: {{날짜}}\n\n더욱 안전한 서비스를 제공해 드리겠습니다.",
                "안녕하세요, {{기관명}}입니다.\n\n신규 대시보드가 출시되었습니다.\n\n- 실시간 통계\n- 사용자 분석\n- 커스텀 위젯\n\n담당자: {{담당자}}",
                "{{기관명}} 개발팀입니다.\n\n사용량 리포트 기능이 추가되었습니다.\n\n월별, 주별 사용량을 확인할 수 있습니다.\n\n{{날짜}}부터 이용 가능합니다.",
                "{{기관명}}입니다.\n\n여름 특별 이벤트를 진행합니다!\n\n기간: {{날짜}} ~ 8월 31일\n\n참여하시면 경품 추첨 기회가 주어집니다.",
                "{{기관명}} 개발팀입니다.\n\nAPI 호출 제한 정책이 변경됩니다.\n\n시행일: {{날짜}}\n\n기존: 1000회/일 → 변경: 5000회/일",
                "{{기관명}} 보안팀입니다.\n\n긴급 보안 패치가 적용되었습니다.\n\n{{날짜}} 기준 모든 취약점이 해결되었습니다.\n\n담당자: {{담당자}}",
                "안녕하세요, {{기관명}}입니다.\n\n베타 테스트 참여자를 모집합니다.\n\n모집 기간: {{날짜}} ~ 2주간\n\n참여자에게는 특별 혜택이 제공됩니다.",
                "{{기관명}}입니다.\n\n서비스 1주년 기념 이벤트!\n\n기간: {{날짜}} ~ 1개월간\n\n감사의 마음을 담아 다양한 혜택을 준비했습니다.",
                "안녕하세요, {{기관명}}입니다.\n\n시스템 업그레이드가 예정되어 있습니다.\n\n일시: {{날짜}} 새벽 3시 ~ 6시\n\n더 나은 서비스로 찾아뵙겠습니다.\n\n담당자: {{담당자}}"
        };

        // 각 공지사항에 맞는 카테고리 인덱스 매핑
        // 0: 일반 공지, 1: 긴급/점검, 2: 이벤트, 3: 업데이트
        int[] categoryIndices = {
                0,  // NoticeFlow 서비스 시작 안내 - 일반 공지
                1,  // 시스템 정기 점검 안내 - 긴급/점검
                3,  // 보안 업데이트 완료 공지 - 업데이트
                3,  // 새로운 기능 업데이트 안내 - 업데이트
                0,  // 서비스 이용약관 변경 안내 - 일반 공지
                0,  // 개인정보처리방침 개정 안내 - 일반 공지
                1,  // 긴급 서버 점검 공지 - 긴급/점검
                0,  // 연말 휴무 안내 - 일반 공지
                0,  // 신규 서비스 출시 안내 - 일반 공지
                3,  // 사용자 매뉴얼 업데이트 - 업데이트
                3,  // 모바일 앱 출시 안내 - 업데이트
                3,  // API 버전 업데이트 공지 - 업데이트
                1,  // 데이터 백업 완료 안내 - 긴급/점검
                1,  // 네트워크 장애 복구 완료 - 긴급/점검
                0,  // 신규 파트너십 체결 안내 - 일반 공지
                2,  // 고객 만족도 조사 실시 - 이벤트
                2,  // 이벤트 당첨자 발표 - 이벤트
                3,  // 서비스 개선 사항 안내 - 업데이트
                3,  // FAQ 업데이트 안내 - 업데이트
                1,  // 결제 시스템 점검 안내 - 긴급/점검
                0,  // 회원 등급 정책 변경 - 일반 공지
                2,  // 포인트 적립 이벤트 안내 - 이벤트
                0,  // 고객센터 운영시간 변경 - 일반 공지
                2,  // 신규 제휴 할인 안내 - 이벤트
                3,  // 앱 업데이트 필수 안내 - 업데이트
                0,  // 개인정보 수집 동의 갱신 - 일반 공지
                1,  // 비밀번호 변경 권고 - 긴급/점검
                3,  // 로그인 보안 강화 안내 - 업데이트
                1,  // 서비스 안정화 완료 공지 - 긴급/점검
                0,  // 2024년 사업 계획 안내 - 일반 공지
                1,  // 클라우드 마이그레이션 완료 - 긴급/점검
                3,  // 신규 결제 수단 추가 안내 - 업데이트
                1,  // 서버 증설 작업 완료 - 긴급/점검
                2,  // 봄맞이 할인 이벤트 - 이벤트
                2,  // 회원 가입 이벤트 연장 - 이벤트
                0,  // 데이터 보존 정책 변경 - 일반 공지
                3,  // 새로운 알림 기능 안내 - 업데이트
                1,  // 주말 긴급 점검 공지 - 긴급/점검
                3,  // 파일 업로드 용량 확대 - 업데이트
                3,  // 다국어 지원 안내 - 업데이트
                0,  // 추석 연휴 운영 안내 - 일반 공지
                1,  // 보안 인증서 갱신 완료 - 긴급/점검
                3,  // 신규 대시보드 출시 - 업데이트
                3,  // 사용량 리포트 기능 추가 - 업데이트
                2,  // 여름 특별 이벤트 안내 - 이벤트
                3,  // API 호출 제한 정책 변경 - 업데이트
                1,  // 긴급 보안 패치 적용 - 긴급/점검
                2,  // 베타 테스트 참여자 모집 - 이벤트
                2,  // 서비스 1주년 기념 이벤트 - 이벤트
                1   // 시스템 업그레이드 예정 안내 - 긴급/점검
        };

        for (int i = 0; i < 50; i++) {
            String title = titles[i];
            String originalContent = contents[i];

            Category selectedCategory = categories[categoryIndices[i] % categories.length];

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

        log.info("50개의 더미 공지사항이 4개 카테고리에 골고루 생성되었습니다.");
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