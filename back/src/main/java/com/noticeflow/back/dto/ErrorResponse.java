package com.noticeflow.back.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String error;
    private String message;
    private String tokenType;

    public ErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
        this.tokenType = null;
    }
}