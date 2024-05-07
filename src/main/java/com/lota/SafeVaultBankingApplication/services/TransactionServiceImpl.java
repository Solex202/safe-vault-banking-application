package com.lota.SafeVaultBankingApplication.services;

import com.lota.SafeVaultBankingApplication.dtos.request.FundTransferDto;
import com.lota.SafeVaultBankingApplication.exceptions.AppException;
import com.lota.SafeVaultBankingApplication.models.Account;
import com.lota.SafeVaultBankingApplication.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.Serializable;

import static com.lota.SafeVaultBankingApplication.exceptions.ExceptionMessages.INVALID_TRANSFER_AMOUNT;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl  implements TransactionService {

    private final AccountRepository accountRepository;
    @Override
    public String performTransfer(String userId,FundTransferDto fundTransferDto) {

        Account senderAccount = accountRepository.findBySafeVaultUserId(userId);
        if(fundTransferDto.getAmount() > senderAccount.getBalance()) throw new AppException(INVALID_TRANSFER_AMOUNT.getMessage());
        double balance = senderAccount.getBalance() - fundTransferDto.getAmount();
        senderAccount.setBalance(balance);

        Account receiverAccount = accountRepository.findByAccountNumber(fundTransferDto.getDestinationAccountNumber());
        receiverAccount.setBalance(receiverAccount.getBalance() + fundTransferDto.getAmount());

        accountRepository.save(senderAccount);
        accountRepository.save(receiverAccount);
        return "Transfer successful";
    }
}
