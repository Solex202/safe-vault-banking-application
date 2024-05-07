package com.lota.SafeVaultBankingApplication.services;

import com.lota.SafeVaultBankingApplication.dtos.request.FundTransferDto;
import com.lota.SafeVaultBankingApplication.exceptions.AppException;
import com.lota.SafeVaultBankingApplication.models.Account;
import com.lota.SafeVaultBankingApplication.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.lota.SafeVaultBankingApplication.exceptions.ExceptionMessages.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl  implements TransactionService {

    private final AccountRepository accountRepository;
    @Override
    public String performTransfer(String userId,FundTransferDto fundTransferDto) {
        if (!accountNumberExists(fundTransferDto.getDestinationAccountNumber())) throw new AppException(ACCOUNT_DOES_NOT_EXISTS.getMessage());
        if(fundTransferDto.getAmount() <= 0) throw new AppException(INVALID_TRANSFER_AMOUNT.getMessage());

        Account senderAccount = accountRepository.findBySafeVaultUserId(userId);
        if(fundTransferDto.getAmount() > senderAccount.getBalance()) throw new AppException(ACCOUNT_BALANCE_EXCEEDED.getMessage());
        double balance = senderAccount.getBalance() - fundTransferDto.getAmount();
        senderAccount.setBalance(balance);

        Account receiverAccount = accountRepository.findByAccountNumberIn(fundTransferDto.getDestinationAccountNumber());
        receiverAccount.setBalance(receiverAccount.getBalance() + fundTransferDto.getAmount());

        accountRepository.save(senderAccount);
        accountRepository.save(receiverAccount);
        return "Transfer Successful";
    }

    private boolean accountNumberExists(String destinationAccountNumber) {
       return accountRepository.existsByAccountNumber(destinationAccountNumber);
    }
}
