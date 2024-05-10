package com.lota.SafeVaultBankingApplication.services;

import com.lota.SafeVaultBankingApplication.dtos.request.FundTransferDto;
import com.lota.SafeVaultBankingApplication.dtos.response.ViewTransactionResponseDto;

public interface TransactionService {

    String performTransfer(String userId,FundTransferDto fundTransferDto);

    ViewTransactionResponseDto viewTransaction(String transactionId);
}
