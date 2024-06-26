package com.lota.SafeVaultBankingApplication.repositories;

import com.lota.SafeVaultBankingApplication.models.Account;
import org.springframework.data.mongodb.core.MongoAdminOperations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface AccountRepository extends MongoRepository<Account, String> {
    Account findBySafeVaultUserId(String userId);

    @Query("{'accountNumber' : ?0}")
    Account findByAccountNumberIn(String destinationAccountNumber);

    @Query(value = "{ 'accountNumber' : ?0 }", exists = true)
    boolean existsByAccountNumber(String destinationAccountNumber);
}
