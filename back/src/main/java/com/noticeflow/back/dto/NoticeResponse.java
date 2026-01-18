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
    private CategoryResponse category;
    private Integer personnel;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static NoticeResponse from(Notice notice) {
        return from(notice, false);
    }

    public static NoticeResponse from(Notice notice, boolean includeContent) {
        return NoticeResponse.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(includeContent ? notice.getContent() : null)
                .category(CategoryResponse.from(notice.getCategory()))
                .personnel(0)
                .createdAt(notice.getCreatedAt())
                .updatedAt(notice.getUpdatedAt())
                .build();
    }
}