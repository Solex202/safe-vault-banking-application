package com.lota.SafeVaultBankingApplication.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class RegisterRequest {

    private String phoneNumber;
    private String email;
    private String passcode;
    private String confirmPasscode;
}
