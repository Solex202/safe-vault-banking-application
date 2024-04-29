package com.lota.SafeVaultBankingApplication.repositories;

import com.lota.SafeVaultBankingApplication.models.SafeVaultUserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SafeVaultUserProfileRepository extends MongoRepository<SafeVaultUserProfile, String> {
    Optional<SafeVaultUserProfile> findBySafeVaultUserId(String userId);
}
