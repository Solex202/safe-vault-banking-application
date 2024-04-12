package com.lota.SafeVaultBankingApplication.exceptions;

import lombok.Getter;

@Getter
public enum SuccessMessage {

    OTP_VERIFIED_SUCCESSFULLY("OTP VERIFIED SUCCESSFULLY");

    private final  String message;
    SuccessMessage(String message){
        this.message = message;
    }

}
