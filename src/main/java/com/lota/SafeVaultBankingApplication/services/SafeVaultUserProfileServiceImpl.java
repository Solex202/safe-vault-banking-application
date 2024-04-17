package com.lota.SafeVaultBankingApplication.services;


import com.lota.SafeVaultBankingApplication.dtos.request.CreateProfileRequest;
import com.lota.SafeVaultBankingApplication.dtos.response.CreateProfileResponse;
import com.lota.SafeVaultBankingApplication.models.SafeVaultUserProfile;
import com.lota.SafeVaultBankingApplication.repositories.SafeVaultUserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
@Slf4j
public class SafeVaultUserProfileServiceImpl implements SafeVaultUserProfileService{
    private final ModelMapper mapper;
    private final SafeVaultUserProfileRepository safeVaultUserProfileRepository;
    @Override
    public CreateProfileResponse createUserProfile(CreateProfileRequest request) {

        SafeVaultUserProfile safeVaultUserProfile = mapper.map(request, SafeVaultUserProfile.class);
        safeVaultUserProfile.setAge(calculateAge(request.getDateOfBirth()));
        return null;
    }


    public int calculateAge(LocalDate dateOfBirth) {
        int age = 0;
        if (dateOfBirth != null) {
            LocalDate currentDate = LocalDate.now();
            Period period = Period.between(dateOfBirth, currentDate);
            age = period.getYears();
        }

        return age;
    }
}
