package com.lota.SafeVaultBankingApplication.services;

import com.lota.SafeVaultBankingApplication.models.SafeVaultUser;

public interface SafeVaultUserService {

    SafeVaultUser getUserBy(String searchParam);

    void processUserPhoneNumber(String phoneNumber);


}
