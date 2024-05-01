package com.lota.SafeVaultBankingApplication.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {


    @Id
    private Long transactionId;

    private String transactionType;

    private Account account;

    private double amount;

    private LocalDateTime timestamp;
}
