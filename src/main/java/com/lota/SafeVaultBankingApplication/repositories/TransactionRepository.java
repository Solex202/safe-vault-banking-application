package com.lota.SafeVaultBankingApplication.repositories;

import com.lota.SafeVaultBankingApplication.models.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
}
