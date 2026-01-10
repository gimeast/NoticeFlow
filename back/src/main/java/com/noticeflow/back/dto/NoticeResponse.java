package com.noticeflow.back.dto;

import com.noticeflow.back.domain.Notice;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeResponse {

    private Long id;
    private String title;
    private String content;
    private String originalContent;
    private CategoryResponse category;
    private TemplateResponse template;
    private UserResponse user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static NoticeResponse from(Notice notice) {
        return NoticeResponse.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .originalContent(notice.getOriginalContent())
                .category(CategoryResponse.from(notice.getCategory()))
                .template(notice.getTemplate() != null ? TemplateResponse.from(notice.getTemplate()) : null)
                .user(UserResponse.from(notice.getUser()))
                .createdAt(notice.getCreatedAt())
                .updatedAt(notice.getUpdatedAt())
                .build();
    }
}