package com.lota.SafeVaultBankingApplication.services;


import com.lota.SafeVaultBankingApplication.dtos.request.UpdateAccountRequest;
import com.lota.SafeVaultBankingApplication.models.Account;
import com.lota.SafeVaultBankingApplication.models.SafeVaultUser;
import com.lota.SafeVaultBankingApplication.repositories.AccountRepository;
import com.lota.SafeVaultBankingApplication.repositories.SafeVaultUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    private final ModelMapper mapper;


    public SafeVaultUser findUserById(String id){
        return userRepository.findById(id)
                .orElseThrow(()-> new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND.getMessage(), id)
                ));
    }

    @Override
    public void generateUserAccountNumber(String userId) {
        SafeVaultUser user = findUserById(userId);
        String randomNumber = generateRandomNumber(7);

        Account account = buildAccount(user, randomNumber);

        accountRepository.save(account);
    }

    private String generateRandomNumber(int length) {
        Random random = new Random();
        return random.ints(0, 10)
                .limit(length)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining());
    }

    private Account buildAccount(SafeVaultUser user, String randomNumber) {
        String accountNumber = user.getPhoneNumber().substring(4) + "031" + randomNumber;
        return Account.builder()
                .safeVaultUser(user)
                .accountNumber(List.of(accountNumber))
                .build();
    }

    public Account updateAccount(String userId, UpdateAccountRequest request){

        Account account = accountRepository.findBySafeVaultUserId(userId);

        mapper.map(request, account);

        account.setDateUpdated(LocalDateTime.now());

        return accountRepository.save(account);
    }
}
