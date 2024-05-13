package com.lota.SafeVaultBankingApplication;

import com.lota.SafeVaultBankingApplication.dtos.request.FundTransferDto;
import com.lota.SafeVaultBankingApplication.dtos.response.ViewTransactionResponseDto;
import com.lota.SafeVaultBankingApplication.exceptions.SafeVaultException;
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
        FundTransferDto dto = FundTransferDto.builder().amount(500).destinationAccountNumber("0317282246").build();

        String response = transactionService.performTransfer("661908db349dd6078964982c", dto);

        assertThat(response, is("Transfer Successful"));

    }

    @Test
    void performTransfer_ThrowExceptionWhenAccountNumberDoesNotExist(){
        FundTransferDto dto = FundTransferDto.builder().amount(1000).destinationAccountNumber("0317282240").build();

        SafeVaultException exception = assertThrows(SafeVaultException.class,()->transactionService.performTransfer("661908db349dd6078964982c", dto));

        assertThat(exception.getMessage(), is("Account number does not exist"));
    }

    @Test
    void performTransfer_ThrowExceptionWhenTransferAmountIsGreaterThanAccountBalance(){
        FundTransferDto dto = FundTransferDto.builder().amount(18000).destinationAccountNumber("0317282246").build();

        SafeVaultException exception = assertThrows(SafeVaultException.class,()->transactionService.performTransfer("661908db349dd6078964982c", dto));

        assertThat(exception.getMessage(), is("Transfer amount is greater than account balance"));
    }

    @Test
    void performTransfer_ThrowExceptionWhenTransferAmountIsLessThanOrEqualToZero(){
        FundTransferDto dto = FundTransferDto.builder().amount(-8000).destinationAccountNumber("0317282246").build();

        SafeVaultException exception = assertThrows(SafeVaultException.class,()->transactionService.performTransfer("661908db349dd6078964982c", dto));

        assertThat(exception.getMessage(), is("Please provide a valid amount"));
    }

    @Test
    void performTransfer_ThrowExceptionWhenTransferAccountIsSameWithDestinationAccount(){
        FundTransferDto dto = FundTransferDto.builder().amount(8000).destinationAccountNumber("0311586370").build();

        SafeVaultException exception = assertThrows(SafeVaultException.class,()->transactionService.performTransfer("661908db349dd6078964982c", dto));

        assertThat(exception.getMessage(), is("Sender and receiver account cannot reference same safe vault user"));
    }

    @Test
    void viewTransactions(){

        ViewTransactionResponseDto responseDto = transactionService.viewTransaction("663b919f51a7fd120193e746");

        assertThat(responseDto.getAmount(), is(500.0));
    }


}
