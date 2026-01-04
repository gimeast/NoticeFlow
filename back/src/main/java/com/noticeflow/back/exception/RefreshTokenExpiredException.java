package com.noticeflow.back.exception;

public class RefreshTokenExpiredException extends TokenExpiredException {
    public RefreshTokenExpiredException(String message) {
        super("REFRESH_TOKEN", message);
    }
}