package com.aliwert.exception;


import lombok.Getter;

@Getter
public enum MessageType {
    NO_RECORD_EXIST("1", "Record not found"),
    TOKEN_IS_EXPIRED("2", "Token expired"),
    GENERAL_EXCEPTION("3", "An error occurred"),
    USERNAME_NOT_FOUND("4", "Username not found"),
    ALREADY_EXISTS("5", "Already exists"),
    REFRESH_TOKEN_NOT_FOUND("6", "Refresh token not found"),
    USERNAME_OR_PASSWORD_INCORRECT("7", "Username or password incorrect"),
    REFRESH_TOKEN_EXPIRED("8", "Refresh token expired"),
    NOT_FOUND("8", "Not found");

    private String code;

    private String message;

    MessageType(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
