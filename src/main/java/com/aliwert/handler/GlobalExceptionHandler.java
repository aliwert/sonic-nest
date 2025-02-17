package com.aliwert.handler;


import com.aliwert.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = {BaseException.class})
    public ResponseEntity<ApiError<String>> handleBaseException(BaseException e, WebRequest request) {
       return ResponseEntity.badRequest().body(createApiError(e.getMessage(), request));
    }
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ApiError<Map<String, List<String>>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, WebRequest request) {
        Map<String, List<String>> map = new HashMap<>();
        for (ObjectError objError : e.getBindingResult().getAllErrors()) {
            String fieldName = ((FieldError) objError).getField();
            if (map.containsKey(fieldName)) {
                map.put(fieldName, addValue(map.get(fieldName), objError.getDefaultMessage()));
            } else {
                map.put(fieldName, addValue(new ArrayList<>(), objError.getDefaultMessage()));
            }
        }

        return ResponseEntity.badRequest().body(createApiError(null, request));

    }

    private List<String> addValue(List<String> allErrors, String newValue) {
        allErrors.add(newValue);
        return allErrors;
    }

    public <E> ApiError<E> createApiError(E message, WebRequest request) {
        ApiError<E> apiError = new ApiError<>();
        apiError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        Exception<E> exception = new Exception<>();
        exception.setPath(request.getDescription(false));
        exception.setCreateTime(new Date());
        exception.setMessage(message);
        exception.setHostName(getHostName());
        apiError.setException(exception);
        return apiError;
    }

    private String getHostName() {
        try {
            Inet4Address.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        return "";
    }
}
