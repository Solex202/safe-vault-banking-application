package com.lota.SafeVaultBankingApplication;


import com.lota.SafeVaultBankingApplication.services.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AccountServiceImplTest {

    @Autowired
    private AccountService accountService;


    @Test
    void setAccountNumber(){

        accountService.generateUserAccountNumber("6637f08d9f73f13847de9da9");
    }
}
