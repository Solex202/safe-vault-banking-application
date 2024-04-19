package com.lota.SafeVaultBankingApplication.services;

import com.lota.SafeVaultBankingApplication.dtos.request.CreateProfileRequest;
import com.lota.SafeVaultBankingApplication.dtos.request.UpdateProfileRequest;
import com.lota.SafeVaultBankingApplication.dtos.response.CreateProfileResponse;
import com.lota.SafeVaultBankingApplication.models.SafeVaultUserProfile;

public interface SafeVaultUserProfileService {

    CreateProfileResponse createUserProfile(String userId, CreateProfileRequest request);

    SafeVaultUserProfile updateUserProfile(String userId, UpdateProfileRequest request);
}
