package com.lota.SafeVaultBankingApplication;

import com.lota.SafeVaultBankingApplication.dtos.request.RegisterRequest;
import com.lota.SafeVaultBankingApplication.services.SafeVaultUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private SafeVaultUserService safeVaultUserService;

    @Test
    void testRegisterUser(){

        RegisterRequest request = RegisterRequest.builder().email("onwuka@gmail.com").phoneNumber("+2349034653698").build();

        //when
        safeVaultUserService.registerUser(request);

        assertThat(request.getEmail(), is("onwuka@gmail.com"));
    }
}
