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
public class Transfer {

    @Id
    private Long transferId;

    private Account sourceAccount;

    private Account destinationAccount;

    private double amount;

    private LocalDateTime timestamp;
}
