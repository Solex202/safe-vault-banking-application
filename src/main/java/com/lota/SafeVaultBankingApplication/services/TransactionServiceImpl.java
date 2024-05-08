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
    public String performTransfer(String userId, FundTransferDto fundTransferDto) {
        validateTransfer(fundTransferDto);

        Account senderAccount = getSenderAccount(userId);
        ensureSufficientBalance(senderAccount, fundTransferDto.getAmount());
        ensureTransferLimit(senderAccount, fundTransferDto.getAmount());
        ensureDifferentSenderReceiverAccounts(senderAccount, fundTransferDto.getDestinationAccountNumber());

        double newSenderBalance = senderAccount.getBalance() - fundTransferDto.getAmount();
        senderAccount.setBalance(newSenderBalance);

        Account receiverAccount = getReceiverAccount(fundTransferDto.getDestinationAccountNumber());
        double newReceiverBalance = receiverAccount.getBalance() + fundTransferDto.getAmount();
        receiverAccount.setBalance(newReceiverBalance);

        saveAccounts(senderAccount, receiverAccount);

        saveTransaction(fundTransferDto, senderAccount, receiverAccount);
        return TRANSFER_SUCCESSFUL.getMessage();
    }

    private void validateTransfer(FundTransferDto fundTransferDto) {
        if (!accountNumberExists(fundTransferDto.getDestinationAccountNumber())) {
            throw new AppException(ACCOUNT_DOES_NOT_EXISTS.getMessage());
        }
        if (fundTransferDto.getAmount() <= 0) {
            throw new AppException(INVALID_TRANSFER_AMOUNT.getMessage());
        }
        //TODO: Add validation for daily limit
    }

    private Account getSenderAccount(String userId) {
        return accountRepository.findBySafeVaultUserId(userId);
    }

    private void ensureSufficientBalance(Account senderAccount, double transferAmount) {
        if (transferAmount > senderAccount.getBalance()) {
            throw new AppException(INSUFFICIENT_BALANCE.getMessage());
        }
    }

    private void ensureTransferLimit(Account senderAccount, double transferAmount) {
        if (transferAmount > senderAccount.getTransferLimit()) {
            throw new AppException(TRANSFER_LIMIT_EXCEEDED.getMessage());
        }
    }

    private void ensureDifferentSenderReceiverAccounts(Account senderAccount, String destinationAccountNumber) {
        if (senderAccount.getAccountNumber().contains(destinationAccountNumber)) {
            throw new AppException("Sender and receiver account cannot reference the same safe vault user");
        }
    }

    private Account getReceiverAccount(String destinationAccountNumber) {
        return accountRepository.findByAccountNumberIn(destinationAccountNumber);
    }

    private void saveAccounts(Account senderAccount, Account receiverAccount) {
        accountRepository.save(senderAccount);
        accountRepository.save(receiverAccount);
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
