package com.lota.SafeVaultBankingApplication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DailyTransferResetScheduler {

    @Autowired
    private AccountService accountService;

    @Scheduled(cron = "02 58 * * * *") // Midnight every day
    public void resetDailyTransferAmount() {
        System.out.println("HAPPENED");
        accountService.resetDailyTransferAmountForAllAccounts();
    }


}
