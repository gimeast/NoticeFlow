package com.noticeflow.back.exception;

public class AccessTokenExpiredException extends TokenExpiredException {
    public AccessTokenExpiredException(String message) {
        super("ACCESS_TOKEN", message);
    }
}