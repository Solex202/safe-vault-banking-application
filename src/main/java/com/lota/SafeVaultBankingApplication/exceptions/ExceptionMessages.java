package com.lota.SafeVaultBankingApplication.exceptions;

import lombok.Getter;

@Getter
public enum ExceptionMessages {

    AUTHENTICATION_FAILURE("FAiled to authenticate request"),

    UNSUPPORTED_AUTHENTICATION_TYPE("Unsupported authentication type"),

    USER_NOT_FOUND("User with email %s not found"),

    INCORRECT_CREDENTIALS("Incorrect credentials supplied"),

    INVALID_PHONENUMBER("Phone number is not valid"),

    INCORRECT_EMAIL("Email is incorrect"),

    ACCOUNT_ALREADY_EXISTS("An account already exists with this phone number")

    ;

    private final String message;

    ExceptionMessages(String message){
        this.message = message;
    }
}
