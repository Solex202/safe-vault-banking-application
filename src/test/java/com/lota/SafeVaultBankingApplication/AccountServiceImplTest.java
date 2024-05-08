package com.lota.SafeVaultBankingApplication;


import com.lota.SafeVaultBankingApplication.dtos.request.UpdateAccountRequest;
import com.lota.SafeVaultBankingApplication.models.Account;
import com.lota.SafeVaultBankingApplication.services.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AccountServiceImplTest {

    @Autowired
    private AccountService accountService;


    @Test
    void setAccountNumber(){

        accountService.generateUserAccountNumber("6637f08d9f73f13847de9da9");
    }

    @Test
    void updateAccountDetails(){
        UpdateAccountRequest request = new UpdateAccountRequest();
        request.setDailyLimit(20000);
        request.setTransferLimit(700000);

        Account account = accountService.updateAccount("6637f08d9f73f13847de9da9", request);

        assertThat(account.getDailyLimit(), is(20000));
    }
}
