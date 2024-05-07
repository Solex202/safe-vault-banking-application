package com.lota.SafeVaultBankingApplication.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {


    @Id
    private String transactionId;

    private String transactionType;

    @DocumentReference
    private Account senderAccount;

    @DocumentReference
    private Account receiverAccount;

    private double amount;

    private String narration;

    private LocalDateTime timestamp;
}
