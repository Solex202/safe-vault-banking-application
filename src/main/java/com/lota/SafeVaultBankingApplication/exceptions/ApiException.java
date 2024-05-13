package com.lota.SafeVaultBankingApplication.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiException {

    private final String message;
    private final HttpStatus httpStatus;

//    private final HttpServletRequest request;

    private final ZonedDateTime timeStamp;


    public ApiException(String message, HttpStatus httpStatus, ZonedDateTime timeStamp) {
        this.message = message;
        this.httpStatus = httpStatus;
//        this.request = httpServletRequest;
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

//    public HttpServletRequest getRequest() {
//        return request;
//    }

    public ZonedDateTime getTimeStamp() {
        return timeStamp;
    }
}
