package com.lota.SafeVaultBankingApplication.services;

import com.lota.SafeVaultBankingApplication.dtos.request.FundTransferDto;

public interface TransactionService {

    String performTransfer(String userId,FundTransferDto fundTransferDto);
}
