package com.lota.SafeVaultBankingApplication.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
    @ExceptionHandler(value = SafeVaultException.class)
    public ResponseEntity<Object> handleAppException(SafeVaultException e){

        ApiException apiException =
                new ApiException(e.getMessage(),
                        badRequest,
                        ZonedDateTime.now(ZoneId.of("Z")));

        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleException(final Exception ex, final HttpServletRequest httpServletRequest) {
        ApiException errorMessage = new ApiException(ex.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z")));
        return new  ResponseEntity<>(errorMessage, badRequest);
    }
}
