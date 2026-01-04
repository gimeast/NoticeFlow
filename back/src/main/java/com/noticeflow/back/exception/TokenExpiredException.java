package com.noticeflow.back.exception;

public class TokenExpiredException extends RuntimeException {
    private final String tokenType;

    public TokenExpiredException(String tokenType, String message) {
        super(message);
        this.tokenType = tokenType;
    }

    public String getTokenType() {
        return tokenType;
    }
}