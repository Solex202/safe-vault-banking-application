package com.lota.SafeVaultBankingApplication.security.providers;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.lota.SafeVaultBankingApplication.exceptions.ExceptionMessages.INCORRECT_CREDENTIALS;

@AllArgsConstructor
@Component
public class SafeVaultAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder encoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String email = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        boolean isPasswordValid = encoder.matches(userDetails.getPassword(), password);
        if(isPasswordValid) return new UsernamePasswordAuthenticationToken(userDetails.getUsername(),null, userDetails.getAuthorities());
        throw new BadCredentialsException(INCORRECT_CREDENTIALS.toString());
        }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }



}
