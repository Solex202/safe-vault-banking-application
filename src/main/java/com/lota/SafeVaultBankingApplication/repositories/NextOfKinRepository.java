package com.lota.SafeVaultBankingApplication.repositories;

import com.lota.SafeVaultBankingApplication.models.NextOfKin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NextOfKinRepository extends MongoRepository<NextOfKin, String> {
}
