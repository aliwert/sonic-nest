package com.aliwert.exception;


import lombok.Getter;

@Getter
public enum MessageType {
    NO_RECORD_EXIST("1", "Record not found"),
    TOKEN_IS_EXPIRED("2", "Token expired"),
    GENERAL_EXCEPTION("3", "An error occurred"),
    USERNAME_NOT_FOUND("4", "Username not found"),
    REFRESH_TOKEN_NOT_FOUND("5", "Refresh token not found"),
    USERNAME_OR_PASSWORD_INCORRECT("6", "Username or password incorrect"),
    REFRESH_TOKEN_EXPIRED("7", "Refresh token expired");

    private String code;

    private String message;

    MessageType(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
