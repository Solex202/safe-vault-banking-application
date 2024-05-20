package com.lota.SafeVaultBankingApplication.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class DailyTransferResetScheduler {

    @Autowired
    private AccountService accountService;

    @Scheduled(cron = "10 03 * * * *") // Midnight every day
    public void resetDailyTransferAmount() {
        log.info("HAPPENED");
        log.info("CRON TIME {}",LocalDateTime.now());
        accountService.resetDailyTransferAmountForAllAccounts();
    }


}
