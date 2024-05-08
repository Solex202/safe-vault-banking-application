package com.lota.SafeVaultBankingApplication.services;

import com.lota.SafeVaultBankingApplication.dtos.request.FundTransferDto;
import com.lota.SafeVaultBankingApplication.exceptions.AppException;
import com.lota.SafeVaultBankingApplication.models.Account;
import com.lota.SafeVaultBankingApplication.models.Transaction;
import com.lota.SafeVaultBankingApplication.repositories.AccountRepository;
import com.lota.SafeVaultBankingApplication.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.lota.SafeVaultBankingApplication.exceptions.ExceptionMessages.*;
import static com.lota.SafeVaultBankingApplication.exceptions.SuccessMessage.TRANSFER_SUCCESSFUL;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl  implements TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public String performTransfer(String userId,FundTransferDto fundTransferDto) {
        if (!accountNumberExists(fundTransferDto.getDestinationAccountNumber())) throw new AppException(ACCOUNT_DOES_NOT_EXISTS.getMessage());
        if(fundTransferDto.getAmount() <= 0) throw new AppException(INVALID_TRANSFER_AMOUNT.getMessage());

        Account senderAccount = accountRepository.findBySafeVaultUserId(userId);
        if(fundTransferDto.getAmount() > senderAccount.getBalance()) throw new AppException(INSUFFICIENT_BALANCE.getMessage());

        if(fundTransferDto.getAmount() > senderAccount.getTransferLimit()) throw new AppException(TRANSFER_LIMIT_EXCEEDED.getMessage());

        senderAccount.getAccountNumber().forEach(accountNumber -> {
                if(accountNumber.equals(fundTransferDto.getDestinationAccountNumber())){
                    throw new AppException("Sender and receiver account cannot reference same safe vault user");
                }
        });
        double balance = senderAccount.getBalance() - fundTransferDto.getAmount();
        senderAccount.setBalance(balance);

        Account receiverAccount = accountRepository.findByAccountNumberIn(fundTransferDto.getDestinationAccountNumber());
        receiverAccount.setBalance(receiverAccount.getBalance() + fundTransferDto.getAmount());

        accountRepository.save(senderAccount);
        accountRepository.save(receiverAccount);

        saveTransaction(fundTransferDto, senderAccount, receiverAccount);
        return TRANSFER_SUCCESSFUL.getMessage();
    }

    private void saveTransaction(FundTransferDto fundTransferDto, Account senderAccount, Account receiverAccount) {
        Transaction transaction = Transaction.builder()
                .transactionType("TRANSFER")
                .amount(fundTransferDto.getAmount())
                .narration(fundTransferDto.getNarration())
                .senderAccount(senderAccount)
                .receiverAccount(receiverAccount)
                .timestamp(LocalDateTime.now())
                .build();

        transactionRepository.save(transaction);
    }

    private boolean accountNumberExists(String destinationAccountNumber) {
       return accountRepository.existsByAccountNumber(destinationAccountNumber);
    }
}
