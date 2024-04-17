package com.lota.SafeVaultBankingApplication.dtos.request;

import com.lota.SafeVaultBankingApplication.models.NextOfKin;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateProfileRequest {

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

    private NextOfKin nextOfKin;
    private String bvn;
}
