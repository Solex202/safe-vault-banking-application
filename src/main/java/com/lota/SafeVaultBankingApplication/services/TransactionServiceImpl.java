package com.lota.SafeVaultBankingApplication.services;

import com.lota.SafeVaultBankingApplication.dtos.request.FundTransferDto;
import com.lota.SafeVaultBankingApplication.dtos.response.ViewTransactionResponseDto;
import com.lota.SafeVaultBankingApplication.exceptions.SafeVaultException;
import com.lota.SafeVaultBankingApplication.models.Account;
import com.lota.SafeVaultBankingApplication.models.Transaction;
import com.lota.SafeVaultBankingApplication.repositories.AccountRepository;
import com.lota.SafeVaultBankingApplication.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.lota.SafeVaultBankingApplication.exceptions.ExceptionMessages.*;
import static com.lota.SafeVaultBankingApplication.exceptions.SuccessMessage.TRANSFER_SUCCESSFUL;
import static com.lota.SafeVaultBankingApplication.utils.AppUtil.paginateDataWith;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl  implements TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final ModelMapper mapper;

    private Transaction getTransaction(String transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(()-> new SafeVaultException(TRANSACTION_NOT_FOUND.getMessage()));
    }

    @Override
    public String performTransfer(String userId, FundTransferDto fundTransferDto) {
        validateTransfer(fundTransferDto);

        Account senderAccount = getSenderAccount(userId);
        ensureSufficientBalance(senderAccount, fundTransferDto.getAmount());
        ensureTransferLimit(senderAccount, fundTransferDto.getAmount());
        ensureDifferentSenderReceiverAccounts(senderAccount, fundTransferDto.getDestinationAccountNumber());

        double newSenderBalance = senderAccount.getAccountBalance() - fundTransferDto.getAmount();
        senderAccount.setAccountBalance(newSenderBalance);

        double newTotalDailyTransferAmount = senderAccount.getTotalDailyTransferAmount() + fundTransferDto.getAmount();
        senderAccount.setTotalDailyTransferAmount(newTotalDailyTransferAmount);

        Account receiverAccount = getReceiverAccount(fundTransferDto.getDestinationAccountNumber());
        double newReceiverBalance = receiverAccount.getAccountBalance() + fundTransferDto.getAmount();
        receiverAccount.setAccountBalance(newReceiverBalance);

        saveAccounts(senderAccount, receiverAccount);

        saveTransaction(fundTransferDto, senderAccount, receiverAccount);
        return TRANSFER_SUCCESSFUL.getMessage();
    }

    private void validateTransfer(FundTransferDto fundTransferDto) {
        if (!accountNumberExists(fundTransferDto.getDestinationAccountNumber())) {
            throw new SafeVaultException(ACCOUNT_DOES_NOT_EXISTS.getMessage());
        }
        if (fundTransferDto.getAmount() <= 0) {
            throw new SafeVaultException(INVALID_TRANSFER_AMOUNT.getMessage());
        }
    }

    private Account getSenderAccount(String userId) {
        return accountRepository.findBySafeVaultUserId(userId);
    }

    private void ensureSufficientBalance(Account senderAccount, double transferAmount) {
        if (transferAmount > senderAccount.getAccountBalance()) {
            throw new SafeVaultException(INSUFFICIENT_BALANCE.getMessage());
        }
    }

    private void ensureTransferLimit(Account senderAccount, double transferAmount) {
        if(senderAccount.getTotalDailyTransferAmount() + transferAmount >= senderAccount.getDailyLimit()) throw new SafeVaultException(DAILY_LIMIT_EXCEEDED.getMessage());
        if (transferAmount > senderAccount.getTransferLimit()) {
            throw new SafeVaultException(TRANSFER_LIMIT_EXCEEDED.getMessage());
        }
    }

    private void ensureDifferentSenderReceiverAccounts(Account senderAccount, String destinationAccountNumber) {
        if (senderAccount.getAccountNumber().contains(destinationAccountNumber)) {
            throw new SafeVaultException(SAME_USER_TRANSFER.getMessage());
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


    @Override
    public ViewTransactionResponseDto viewTransaction(String transactionId){
        Transaction transaction = getTransaction(transactionId);
        ViewTransactionResponseDto dto = mapper.map(transaction, ViewTransactionResponseDto.class);
        dto.setReceiverAccountNumber(transaction.getReceiverAccount().getAccountNumber().get(1));
        dto.setSenderAccountNumber(transaction.getSenderAccount().getAccountNumber().get(1));

        log.info("TRANSACTION RESPONSE {}", dto);
        return dto;
    }

    public Page<ViewTransactionResponseDto> viewAllTransactions(int page, int size){
        Pageable pageRequest = paginateDataWith(page, size);
        Page<Transaction> userList = transactionRepository.findAll(pageRequest);

        return buildTransactionResponseFrom(userList);
    }

    private Page<ViewTransactionResponseDto> buildTransactionResponseFrom(Page<Transaction> transactions) {

        return transactions
                .map(transaction -> mapper.map(transaction, ViewTransactionResponseDto.class));
    }


}
