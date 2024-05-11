package com.lota.SafeVaultBankingApplication.services;

import com.lota.SafeVaultBankingApplication.dtos.request.FundTransferDto;
import com.lota.SafeVaultBankingApplication.dtos.response.ViewTransactionResponseDto;

import java.util.List;

public interface TransactionService {

    String performTransfer(String userId,FundTransferDto fundTransferDto);

    List<ViewTransactionResponseDto> viewAllTransactions(int page, int size);

    ViewTransactionResponseDto viewTransaction(String transactionId);
}
