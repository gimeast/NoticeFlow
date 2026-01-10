package com.noticeflow.back.service;

import com.noticeflow.back.domain.Template;
import com.noticeflow.back.domain.User;
import com.noticeflow.back.domain.UserRole;
import com.noticeflow.back.dto.TemplateResponse;
import com.noticeflow.back.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TemplateService {

    private final TemplateRepository templateRepository;

    public List<TemplateResponse> getAllTemplates(User user) {
        validateOrganizationUser(user);

        List<Template> templates = templateRepository.findAllByOrderByCreatedAtDesc();
        return templates.stream()
                .map(TemplateResponse::from)
                .collect(Collectors.toList());
    }

    public TemplateResponse getDefaultTemplate(User user) {
        validateOrganizationUser(user);

        Template template = templateRepository.findByIsDefaultTrue()
                .orElseThrow(() -> new IllegalStateException("기본 템플릿을 찾을 수 없습니다"));
        return TemplateResponse.from(template);
    }

    public TemplateResponse getTemplate(User user, Long templateId) {
        validateOrganizationUser(user);

        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new IllegalArgumentException("템플릿을 찾을 수 없습니다"));
        return TemplateResponse.from(template);
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