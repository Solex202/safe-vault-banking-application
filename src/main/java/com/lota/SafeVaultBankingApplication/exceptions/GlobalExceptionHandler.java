package com.lota.SafeVaultBankingApplication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class GlobalExceptionHandler {

    @ExceptionHandler()
    public ResponseEntity<Object> handleAppException(AppException e){

        ApiException apiException =
                new ApiException(e.getMessage(),
                        HttpStatus.BAD_REQUEST,
                        ZonedDateTime.now(ZoneId.of("Z")));

        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }
}
