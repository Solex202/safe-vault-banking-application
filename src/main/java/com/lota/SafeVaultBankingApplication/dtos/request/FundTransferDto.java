package com.lota.SafeVaultBankingApplication.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class FundTransferDto {

//    private String  userId;

    private String destinationAccountNumber;

    private double amount;
}
