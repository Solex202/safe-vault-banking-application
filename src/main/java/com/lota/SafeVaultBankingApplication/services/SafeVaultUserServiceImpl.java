package com.lota.SafeVaultBankingApplication.services;

import com.lota.SafeVaultBankingApplication.config.SmsSender;
import com.lota.SafeVaultBankingApplication.dtos.request.RegisterRequest;
import com.lota.SafeVaultBankingApplication.exceptions.AppException;
import com.lota.SafeVaultBankingApplication.models.SafeVaultUser;
import com.lota.SafeVaultBankingApplication.repositories.SafeVaultUserRepository;
import com.lota.SafeVaultBankingApplication.security.models.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.lota.SafeVaultBankingApplication.exceptions.ExceptionMessages.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class SafeVaultUserServiceImpl implements SafeVaultUserService, UserDetailsService {

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SafeVaultUser user = getUserBy(username);
        return new Principal(user);
    }

    @Override
    public void registerUser(RegisterRequest request) {
        validate(request.getPhoneNumber());
        SmsSender.sendSmsTo(request.getPhoneNumber());
    }

    private void validate(String phoneNumber){
        String regex = "((^+)(234){1}[0–9]{10})|((^234)[0–9]{10})|((^0)(7|8|9){1}(0|1){1}[0–9]{8})";
        if (!phoneNumber.matches(regex)) throw new AppException(INVALID_PHONENUMBER.toString());

    }

}
