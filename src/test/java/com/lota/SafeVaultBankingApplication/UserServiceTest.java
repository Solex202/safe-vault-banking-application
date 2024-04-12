package com.lota.SafeVaultBankingApplication;

import com.lota.SafeVaultBankingApplication.services.SafeVaultUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private SafeVaultUserService safeVaultUserService;

    @Test
    void processPhoneNumber(){

        safeVaultUserService.processUserPhoneNumber("+2349034653698");

    }

    @Test
    void validateOtp(){
        String response = safeVaultUserService.validateOtp("850103", "6617d1bcf79f1d2c309e721f");

        assertThat(response, is("OTP VERIFIED SUCCESSFULLY"));
    }
}
