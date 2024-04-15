package com.lota.SafeVaultBankingApplication.exceptions;

import lombok.Getter;

@Getter
public enum SuccessMessage {

    OTP_VERIFIED_SUCCESSFULLY("OTP VERIFIED SUCCESSFULLY"),

    OTP_RESENT_SUCCESSFULLY("A new otp has been sent to your inbox"),

    PASSCODE_SET_SUCCESSFULLY("Passcode set successfully, continue")
    ;

    private final  String message;
    SuccessMessage(String message){
        this.message = message;
    }

}
