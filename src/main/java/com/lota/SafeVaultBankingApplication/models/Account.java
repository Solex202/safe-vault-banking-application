package com.lota.SafeVaultBankingApplication.models;

import com.lota.SafeVaultBankingApplication.models.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    private String accountId;
    @DocumentReference
    private SafeVaultUser safeVaultUser;
    private AccountType accountType;
    private List<String> accountNumber;
    private double balance;
    private int dailyLimit;
    private int transferLimit;
    private double totalDailyTransferAmount;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
}
