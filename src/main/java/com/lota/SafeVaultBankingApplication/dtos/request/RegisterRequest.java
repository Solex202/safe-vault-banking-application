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

    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
