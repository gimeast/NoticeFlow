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

    @Transactional
    public CategoryResponse createCategory(User user, CategoryRequest request) {
        validateOrganizationUser(user);

        Organization organization = user.getOrganization();

        Category category = Category.builder()
                .organization(organization)
                .name(request.getName())
                .isVisible(true)
                .build();

        category = categoryRepository.save(category);
        return CategoryResponse.from(category);
    }

    public List<CategoryResponse> getCategories(User user, boolean includeHidden) {
        Organization organization = user.getOrganization();
        if (organization == null) {
            throw new IllegalStateException("기관 정보가 없습니다");
        }

        List<Category> categories;
        if (includeHidden) {
            categories = categoryRepository.findByOrganizationOrderByCreatedAtDesc(organization);
        } else {
            categories = categoryRepository.findByOrganizationAndIsVisibleTrueOrderByCreatedAtDesc(organization);
        }

        return categories.stream()
                .map(CategoryResponse::from)
                .collect(Collectors.toList());
    }

    public CategoryResponse getCategory(User user, Long categoryId) {
        Organization organization = user.getOrganization();
        if (organization == null) {
            throw new IllegalStateException("기관 정보가 없습니다");
        }

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