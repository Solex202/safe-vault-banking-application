package com.lota.SafeVaultBankingApplication.services;

import com.lota.SafeVaultBankingApplication.dtos.request.SetPasscodeDto;
import com.lota.SafeVaultBankingApplication.dtos.response.PaginatedResponse;
import com.lota.SafeVaultBankingApplication.dtos.response.UserResponseDto;
import com.lota.SafeVaultBankingApplication.models.SafeVaultUser;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SafeVaultUserService {

    SafeVaultUser getUserBy(String searchParam);

    void processUserPhoneNumber(String phoneNumber);

    String regenerateOtp(String userId);

    void processUserEmail( String userId,String email);

    String validateOtp(String userId,String otp);

    SafeVaultUser findUserById(String id);

    String setPasscode(String userId, SetPasscodeDto setPasscodeDto);

    UserResponseDto viewCustomer(String userId);

    Page<UserResponseDto> viewAllCustomers(int page, int size);

    boolean isMethodEffectOccurred();





}
