package com.lota.SafeVaultBankingApplication.dtos.request;

import com.lota.SafeVaultBankingApplication.models.NextOfKin;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
@NoArgsConstructor
@Data
public class CreateProfileRequest {

    private String dob;
    private int age;
    private String gender;
    private String motherMaidenName;

    //contact information
    private String phoneNumber;
    private String address;
    private String stateOfResidence;
    private String stateOfOrigin;
    //employment information
    private String occupation;
    private String employerName;
    private String employerContact;

    private NextOfKin nextOfKin;
    private String bvn;
}
