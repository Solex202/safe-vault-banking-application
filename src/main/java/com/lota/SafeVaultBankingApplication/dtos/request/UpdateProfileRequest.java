package com.lota.SafeVaultBankingApplication.dtos.request;

import com.lota.SafeVaultBankingApplication.models.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class UpdateProfileRequest {

    private LocalDate dateOfBirth;
    private int age;
    private Gender gender;
    private String motherMaidenName;
    //contact information
    private String address;
    private String stateOfResidence;
    private String lgaOfResidence;
    private String stateOfOrigin;
    //employment information
    private String occupation;
    private String employerName;
    private String employerContact;

    private String bvn;
}
