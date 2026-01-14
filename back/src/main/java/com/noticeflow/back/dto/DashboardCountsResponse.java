package com.noticeflow.back.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardCountsResponse {

    private CountItem totalNotices;
    private CountItem sentNotices;
    private CountItem registeredUsers;
    private CountItem templates;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CountItem {
        @Builder.Default
        private Long count = 0L;
    }

    public static DashboardCountsResponse of(Long totalNotices, Long sentNotices, Long registeredUsers, Long templates) {
        return DashboardCountsResponse.builder()
                .totalNotices(CountItem.builder().count(totalNotices != null ? totalNotices : 0L).build())
                .sentNotices(CountItem.builder().count(sentNotices != null ? sentNotices : 0L).build())
                .registeredUsers(CountItem.builder().count(registeredUsers != null ? registeredUsers : 0L).build())
                .templates(CountItem.builder().count(templates != null ? templates : 0L).build())
                .build();
    }
}