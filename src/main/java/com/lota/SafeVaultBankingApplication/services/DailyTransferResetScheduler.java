package com.lota.SafeVaultBankingApplication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DailyTransferResetScheduler {

    @Autowired
    private AccountService accountService;

    @Scheduled(cron = "10 03 * * * *") // Midnight every day
    public void resetDailyTransferAmount() {
        System.out.println("HAPPENED");
        System.out.println(LocalDateTime.now());
        accountService.resetDailyTransferAmountForAllAccounts();
    }


}
