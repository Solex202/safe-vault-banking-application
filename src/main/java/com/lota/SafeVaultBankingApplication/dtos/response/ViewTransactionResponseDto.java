package com.lota.SafeVaultBankingApplication.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Builder
public class ViewTransactionResponseDto {

    private String transactionType;

    private String senderAccountNumber;

    private String receiverAccountNumber;

    private double amount;

    private String narration;

    private LocalDateTime timestamp;
}
