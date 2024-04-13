package com.lota.SafeVaultBankingApplication.services;

import com.lota.SafeVaultBankingApplication.models.SafeVaultUser;

public interface SafeVaultUserService {

    SafeVaultUser getUserBy(String searchParam);

    void processUserPhoneNumber(String phoneNumber);

    String regenerateOtp(String userId);

    void processUserEmail(String email, String userId);

    String validateOtp(String otp, String userId);

    SafeVaultUser findUserById(String id);


}
