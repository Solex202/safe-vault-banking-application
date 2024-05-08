package com.lota.SafeVaultBankingApplication.services;

import com.lota.SafeVaultBankingApplication.dtos.request.UpdateAccountRequest;
import com.lota.SafeVaultBankingApplication.models.Account;

public interface AccountService {

    void generateUserAccountNumber(String userId);

    Account updateAccount(String userId, UpdateAccountRequest request);
}
