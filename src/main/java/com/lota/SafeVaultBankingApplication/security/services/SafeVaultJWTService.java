package com.lota.SafeVaultBankingApplication.security.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface SafeVaultJWTService {
    String generateTokenFor(String email);

    UserDetails extraUserDetailsFrom(String token);
}
