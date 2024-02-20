package com.lota.SafeVaultBankingApplication.exceptions;

import lombok.Getter;

@Getter
public enum ExceptionMessages {

    AUTHENTICATION_FAILURE("FAiled to authenticate request");

    private final String message;

    ExceptionMessages(String message){
        this.message = message;
    }
}
