package com.lota.SafeVaultBankingApplication.services;

import com.lota.SafeVaultBankingApplication.models.SafeVaultUser;
import com.lota.SafeVaultBankingApplication.repositories.SafeVaultUserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.lota.SafeVaultBankingApplication.exceptions.ExceptionMessages.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class SafeVaultUserServiceImpl implements SafeVaultUserService{

    private final SafeVaultUserRepository userRepository;
    @Override
    public SafeVaultUser getUserBy(String searchParam) {
        return findUserByEmail(searchParam);
    }

    private SafeVaultUser findUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND.getMessage(), email)
                ));
    }
}
