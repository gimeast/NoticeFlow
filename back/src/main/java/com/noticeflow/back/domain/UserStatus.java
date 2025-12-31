package com.noticeflow.back.domain;

public enum UserStatus {
    PENDING,    // OAuth2 로그인만 완료, 추가 정보 미입력
    COMPLETED   // 가입 완료 (일반/기관 등록 완료)
}