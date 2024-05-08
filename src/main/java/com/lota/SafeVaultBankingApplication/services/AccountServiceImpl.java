package com.lota.SafeVaultBankingApplication.services;


import com.lota.SafeVaultBankingApplication.models.Account;
import com.lota.SafeVaultBankingApplication.models.SafeVaultUser;
import com.lota.SafeVaultBankingApplication.repositories.AccountRepository;
import com.lota.SafeVaultBankingApplication.repositories.SafeVaultUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.lota.SafeVaultBankingApplication.exceptions.ExceptionMessages.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;

    private final SafeVaultUserRepository userRepository;


    public SafeVaultUser findUserById(String id){
        return userRepository.findById(id)
                .orElseThrow(()-> new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND.getMessage(), id)
                ));
    }

    public void generateUserAccountNumber(String userId){

        SafeVaultUser user = findUserById(userId);
        Random random = new Random();

        String randomNumber = IntStream.range(0, 7)
                .mapToObj(i -> String.valueOf(random.nextInt(10)))
                .collect(Collectors.joining());

        Account account = Account.builder()
                .safeVaultUser(user)
                .accountNumber(List.of(user.getPhoneNumber().substring(4), "031" + randomNumber))
                .build();

        accountRepository.save(account);
    }
}
