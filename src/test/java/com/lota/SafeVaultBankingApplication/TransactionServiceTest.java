package com.lota.SafeVaultBankingApplication;

import com.lota.SafeVaultBankingApplication.dtos.request.FundTransferDto;
import com.lota.SafeVaultBankingApplication.services.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Test
    void performTransfer(){
        FundTransferDto dto =FundTransferDto.builder().amount(1000).destinationAccountNumber("0317282247").build();

        String response = transactionService.performTransfer("661908db349dd6078964982c", dto);

        assertThat(response, is("Transfer Successful"));


    }
}
