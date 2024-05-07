package com.lota.SafeVaultBankingApplication.services;

import com.lota.SafeVaultBankingApplication.dtos.response.UserResponseDto;
import com.lota.SafeVaultBankingApplication.models.SafeVaultUser;

import java.util.List;

public interface SafeVaultUserService {

    SafeVaultUser getUserBy(String searchParam);

    void processUserPhoneNumber(String phoneNumber);

    String regenerateOtp(String userId);

    void processUserEmail(String email, String userId);

    String validateOtp(String otp, String userId);

    SafeVaultUser findUserById(String id);

    String setPasscode(String userId, String passcode, String confirmPasscode);

    void generateUserAccountNumber(String userId);

    UserResponseDto viewCustomer(String userId);

    List<UserResponseDto> viewAllCustomers(int page, int size);

    boolean isMethodEffectOccurred();





}
