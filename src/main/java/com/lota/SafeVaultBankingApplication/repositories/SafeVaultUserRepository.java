package com.lota.SafeVaultBankingApplication.repositories;

import com.lota.SafeVaultBankingApplication.models.SafeVaultUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SafeVaultUserRepository extends MongoRepository<SafeVaultUser, String> {
    Optional<SafeVaultUser> findByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);
}
