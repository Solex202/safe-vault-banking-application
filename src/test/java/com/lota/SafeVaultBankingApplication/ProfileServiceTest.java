package com.lota.SafeVaultBankingApplication;

import com.lota.SafeVaultBankingApplication.dtos.request.CreateProfileRequest;
import com.lota.SafeVaultBankingApplication.dtos.response.CreateProfileResponse;
import com.lota.SafeVaultBankingApplication.services.SafeVaultUserProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProfileServiceTest {

    @Autowired
    private SafeVaultUserProfileService profileService;


    @Test
    void createProfile(){

        CreateProfileRequest request = CreateProfileRequest.builder()
                .dateOfBirth(LocalDate.of(1992, 3,21))
                .gender("MALE")
                .stateOfOrigin("Original state")
                .bvn("322213232121")
                .build();

        CreateProfileResponse response = profileService.createUserProfile("661908db349dd6078964982c", request);
        assertThat(response.getAge(), is(28));


    }
}
