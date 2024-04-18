package com.lota.SafeVaultBankingApplication.dtos.response;

import com.lota.SafeVaultBankingApplication.models.NextOfKin;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CreateProfileResponse {
    private LocalDate dateOfBirth;
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

//    private NextOfKin nextOfKin;
    private String bvn;

    private String nextOfKinFirstname;
    private String nextOfLastname;
    private String nextOfKinEmailAddress;
    private String nextOfPhoneNumber;
    private String nextOfKinAddress;
    private String relationship;

}
