package com.lota.SafeVaultBankingApplication;

import com.lota.SafeVaultBankingApplication.exceptions.AppException;
import com.lota.SafeVaultBankingApplication.models.SafeVaultUser;
import com.lota.SafeVaultBankingApplication.services.SafeVaultUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private SafeVaultUserService safeVaultUserService;

    @Test
    void processPhoneNumber() {

        safeVaultUserService.processUserPhoneNumber("+2349034653698");
        assertTrue(safeVaultUserService.isMethodEffectOccurred());
    }

    @Test
    void processPhoneNumber_ThrowExceptionIfPhoneNumberAlreadyExists() {
        assertThrows(AppException.class, () -> safeVaultUserService.processUserPhoneNumber("+2349034653698"));
    }

    @Test
    void processPhoneNumber_ThrowExceptionIfPhoneNumberIsEmpty() {
        assertThrows(AppException.class, () -> safeVaultUserService.processUserPhoneNumber(""));
    }


    @Test
    void validateOtp() {
        SafeVaultUser user = safeVaultUserService.findUserById("661908db349dd6078964982c");
        assertFalse(user.isOtpVerified());
        String response = safeVaultUserService.validateOtp("670376", "661908db349dd6078964982c");

        assertThat(response, is("OTP VERIFIED SUCCESSFULLY"));
    }

    @Test
    void validateOtp_ThrowException_If_UserIdIsNotValid() {

        assertThrows(UsernameNotFoundException.class, () -> safeVaultUserService.validateOtp("670376", "invalid id"));
    }

    @Test
    void validateOtp_ThrowException_If_OtpIsNull() {

        assertThrows(AppException.class, () -> safeVaultUserService.validateOtp("", "661908db349dd6078964982c"));
    }

    @Test
    void validateOtp_ThrowException_If_OtpHasExpired() {

        assertThrows(AppException.class, () -> safeVaultUserService.validateOtp("670376", "661908db349dd6078964982c"));
    }

    @Test
    void testRegenerateOtp() {

        String response = safeVaultUserService.regenerateOtp("661908db349dd6078964982c");
        assertThat(response, is("A new otp has been sent to your inbox"));
    }

    @Test
    void testEmailProcessing() {
        safeVaultUserService.processUserEmail("onwukalotachukwu210@gmail.com", "661908db349dd6078964982c");

        assertTrue(safeVaultUserService.isMethodEffectOccurred());
    }

    @Test
    void testEmailProcessing_ThrowExceptionWhenEmailIsInvalid() {
        assertThrows(AppException.class, () -> safeVaultUserService.processUserEmail("onwukalotachukwu@", "661908db349dd6078964982c"));
    }

    @Test
    void testEmailProcessing_ThrowExceptionWhenEmailIsNull() {
        assertThrows(AppException.class, () -> safeVaultUserService.processUserEmail("", "661908db349dd6078964982c"));
    }

    @Test
    void testSetPasscode(){

        String response = safeVaultUserService.setPasscode("661908db349dd6078964982c", "121323", "121323");
        assertThat(response, is("Passcode set successfully, continue"));
    }

    @Test
    void testSetPasscode_ThrowExceptionIfPasscodesDoesNotMatch(){

        AppException exception = assertThrows(AppException.class, ()->safeVaultUserService.setPasscode("661908db349dd6078964982c", "121323", "12132324444"));
        assertThat(exception.getMessage(), is("Passcodes must match"));
    }

    @Test
    void setAccountNumber(){

        safeVaultUserService.setAccountNumber("661908db349dd6078964982c");
    }
}
