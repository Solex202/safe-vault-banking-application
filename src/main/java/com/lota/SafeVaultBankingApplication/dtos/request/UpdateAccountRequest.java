package com.lota.SafeVaultBankingApplication.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UpdateAccountRequest {

    private int dailyLimit;
    private int transferLimit;
    private String accountType;
}
