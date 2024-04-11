package com.lota.SafeVaultBankingApplication.services;

import com.lota.SafeVaultBankingApplication.dtos.request.RegisterRequest;
import com.lota.SafeVaultBankingApplication.models.SafeVaultUser;

public interface SafeVaultUserService {

    SafeVaultUser getUserBy(String searchParam);

    void registerUser(String phoneNumber);


}
