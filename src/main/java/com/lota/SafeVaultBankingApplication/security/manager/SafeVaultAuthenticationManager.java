package com.lota.SafeVaultBankingApplication.security.manager;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import static com.lota.SafeVaultBankingApplication.exceptions.ExceptionMessages.UNSUPPORTED_AUTHENTICATION_TYPE;

@AllArgsConstructor
@Component
public class SafeVaultAuthenticationManager implements AuthenticationManager {

    private final AuthenticationProvider authenticationProvider;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        boolean isAuthenticationSupported = authenticationProvider.supports(authentication.getClass());
        if (isAuthenticationSupported) authenticationProvider.authenticate(authentication);

        throw new ProviderNotFoundException(UNSUPPORTED_AUTHENTICATION_TYPE.getMessage());
    }
}
