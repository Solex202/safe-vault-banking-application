package com.lota.SafeVaultBankingApplication.repositories;

import com.lota.SafeVaultBankingApplication.models.SafeVaultUserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SafeVaultUserProfileRepository extends MongoRepository<SafeVaultUserProfile, String> {
}
