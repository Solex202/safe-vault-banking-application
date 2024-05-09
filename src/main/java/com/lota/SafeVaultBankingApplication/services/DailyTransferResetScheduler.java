package com.lota.SafeVaultBankingApplication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DailyTransferResetScheduler {

    @Autowired
    private AccountService accountService;

    @Scheduled(cron = "16 12 * * * *") // Midnight every day
    public void resetDailyTransferAmount() {
        System.out.println("HAPPENDED");
        accountService.resetDailyTransferAmountForAllAccounts();
    }

//    @Scheduled(cron = "10 12 * * * *") // Midnight every day
//    public void saySomething() {
//        System.out.println("THIS IS A THURSDAY AFTERNOON");
//    }
}
