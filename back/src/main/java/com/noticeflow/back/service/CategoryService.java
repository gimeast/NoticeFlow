package com.noticeflow.back.service;

import com.noticeflow.back.domain.Category;
import com.noticeflow.back.domain.Organization;
import com.noticeflow.back.domain.User;
import com.noticeflow.back.domain.UserRole;
import com.noticeflow.back.dto.CategoryRequest;
import com.noticeflow.back.dto.CategoryResponse;
import com.noticeflow.back.repository.CategoryRepository;
import com.noticeflow.back.repository.NoticeRepository;
import com.noticeflow.back.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final NoticeRepository noticeRepository;
    private final OrganizationRepository organizationRepository;

    private static final int MAX_CATEGORY_COUNT = 4;

    @Transactional
    public CategoryResponse createCategory(User user, CategoryRequest request) {
        validateOrganizationUser(user);

        Organization organization = user.getOrganization();

        // 카테고리 개수 제한 체크
        long categoryCount = categoryRepository.countByOrganization(organization);
        if (categoryCount >= MAX_CATEGORY_COUNT) {
            throw new IllegalStateException("카테고리는 최대 " + MAX_CATEGORY_COUNT + "개까지만 생성할 수 있습니다");
        }

        Category category = Category.builder()
                .organization(organization)
                .name(request.getName())
                .isVisible(true)
                .build();

        category = categoryRepository.save(category);
        return CategoryResponse.from(category);
    }

    public List<CategoryResponse> getCategories(User user, boolean includeHidden) {
        Organization organization = getOrganizationByUser(user);

        List<Category> categories;
        // includeHidden은 기관 관리자만 사용 가능
        if (includeHidden && user.getRole() == UserRole.ORGANIZATION) {
            categories = categoryRepository.findByOrganizationOrderByCreatedAtDesc(organization);
        } else {
            categories = categoryRepository.findByOrganizationAndIsVisibleTrueOrderByCreatedAtDesc(organization);
        }

        return categories.stream()
                .map(CategoryResponse::from)
                .collect(Collectors.toList());
    }

    private Organization getOrganizationByUser(User user) {
        if (user.getRole() == UserRole.ORGANIZATION) {
            // 기관 관리자: 자신의 기관
            if (user.getOrganization() == null) {
                throw new IllegalStateException("기관 정보가 없습니다");
            }
            return user.getOrganization();
        } else {
            // 구독자 등: 연결된 기관에서 가져오기
            if (user.getNormalUsers() == null || user.getNormalUsers().isEmpty()) {
                throw new IllegalStateException("연결된 기관이 없습니다");
            }
            return user.getNormalUsers().get(0).getOrganization();
        }
    }

    public CategoryResponse getCategory(User user, Long categoryId) {
        Organization organization = getOrganizationByUser(user);

        Category category = categoryRepository.findByIdAndOrganization(categoryId, organization)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다"));

        return CategoryResponse.from(category);
    }

    @Transactional
    public CategoryResponse updateCategory(User user, Long categoryId, CategoryRequest request) {
        validateOrganizationUser(user);

        Organization organization = user.getOrganization();

        Category category = categoryRepository.findByIdAndOrganization(categoryId, organization)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다"));

        category.setName(request.getName());
        category = categoryRepository.save(category);

        return CategoryResponse.from(category);
    }

    @Transactional
    public void hideCategory(User user, Long categoryId) {
        validateOrganizationUser(user);

        Organization organization = user.getOrganization();

        Category category = categoryRepository.findByIdAndOrganization(categoryId, organization)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다"));

        // 해당 카테고리를 사용하는 공지사항이 있는지 확인
        if (noticeRepository.existsByCategory(category)) {
            throw new IllegalStateException("해당 카테고리를 사용하는 공지사항이 있어 숨길 수 없습니다");
        }

        category.setIsVisible(false);
        categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(User user, Long categoryId) {
        validateOrganizationUser(user);

        Organization organization = user.getOrganization();

        Category category = categoryRepository.findByIdAndOrganization(categoryId, organization)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다"));

        // 해당 카테고리를 사용하는 공지사항이 있는지 확인
        if (noticeRepository.existsByCategory(category)) {
            throw new IllegalStateException("해당 카테고리를 사용하는 공지사항이 있어 삭제할 수 없습니다");
        }

        categoryRepository.delete(category);
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