package com.lota.SafeVaultBankingApplication.services;


import com.lota.SafeVaultBankingApplication.dtos.request.CreateProfileRequest;
import com.lota.SafeVaultBankingApplication.dtos.response.CreateProfileResponse;
import com.lota.SafeVaultBankingApplication.models.NextOfKin;
import com.lota.SafeVaultBankingApplication.models.SafeVaultUser;
import com.lota.SafeVaultBankingApplication.models.SafeVaultUserProfile;
import com.lota.SafeVaultBankingApplication.repositories.NextOfKinRepository;
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
    private final SafeVaultUserService userService;
    private final NextOfKinRepository nextOfKinRepository;
    @Override
    public CreateProfileResponse createUserProfile(String userId, CreateProfileRequest request) {

        SafeVaultUser user = userService.findUserById(userId);
        SafeVaultUserProfile safeVaultUserProfile = mapper.map(request, SafeVaultUserProfile.class);
        safeVaultUserProfile.setAge(calculateAge(request.getDateOfBirth()));
        safeVaultUserProfile.setSafeVaultUser(user);

        NextOfKin nextOfKin = NextOfKin.builder()
                .safeVaultUser(user)
                .address(request.getNextOfKinAddress())
                .emailAddress(request.getNextOfKinEmailAddress())
                .firstname(request.getNextOfKinFirstname())
                .lastname(request.getNextOfLastname())
                .relationship(request.getRelationship())
                .build();

        nextOfKinRepository.save(nextOfKin);

        SafeVaultUserProfile savedProfile = safeVaultUserProfileRepository.save(safeVaultUserProfile);

        log.info("PROFILE RESPONSE {}",  mapper.map(savedProfile, CreateProfileResponse.class));
        return mapper.map(savedProfile, CreateProfileResponse.class);
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
