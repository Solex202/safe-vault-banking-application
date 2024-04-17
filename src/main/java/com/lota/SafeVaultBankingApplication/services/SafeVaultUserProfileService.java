package com.lota.SafeVaultBankingApplication.services;

import com.lota.SafeVaultBankingApplication.dtos.request.CreateProfileRequest;
import com.lota.SafeVaultBankingApplication.dtos.response.CreateProfileResponse;

public interface SafeVaultUserProfileService {

    CreateProfileResponse createUserProfile(String userId, CreateProfileRequest request);
}
