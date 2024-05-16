package com.lota.SafeVaultBankingApplication.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SetPasscodeDto {

    String passcode;
    String confirmPasscode;
}
