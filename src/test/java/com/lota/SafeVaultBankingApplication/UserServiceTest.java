package com.lota.SafeVaultBankingApplication;

import com.lota.SafeVaultBankingApplication.dtos.response.UserResponseDto;
import com.lota.SafeVaultBankingApplication.exceptions.SafeVaultException;
import com.lota.SafeVaultBankingApplication.models.SafeVaultUser;
import com.lota.SafeVaultBankingApplication.services.SafeVaultUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

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
        assertThrows(SafeVaultException.class, () -> safeVaultUserService.processUserPhoneNumber("+2349034653698"));
    }

    @Test
    void processPhoneNumber_ThrowExceptionIfPhoneNumberIsEmpty() {
        assertThrows(SafeVaultException.class, () -> safeVaultUserService.processUserPhoneNumber(""));
    }


    @Test
    void validateOtp() {
        SafeVaultUser user = safeVaultUserService.findUserById("6637f08d9f73f13847de9da9");
        assertFalse(user.isOtpVerified());
        String response = safeVaultUserService.validateOtp("6637f08d9f73f13847de9da9","098456");

        assertThat(response, is("OTP VERIFIED SUCCESSFULLY"));
    }

    @Test
    void validateOtp_ThrowException_If_UserIdIsNotValid() {

        assertThrows(UsernameNotFoundException.class, () -> safeVaultUserService.validateOtp("invalid id","670376" ));
    }

    @Test
    void validateOtp_ThrowException_If_OtpIsNull() {

        assertThrows(SafeVaultException.class, () -> safeVaultUserService.validateOtp("661908db349dd6078964982c","" ));
    }

    @Test
    void validateOtp_ThrowException_If_OtpHasExpired() {

        assertThrows(SafeVaultException.class, () -> safeVaultUserService.validateOtp("661908db349dd6078964982c","670376"));
    }

    @Test
    void testRegenerateOtp() {

        String response = safeVaultUserService.regenerateOtp("661908db349dd6078964982c");
        assertThat(response, is("A new otp has been sent to your inbox"));
    }

    @Test
    void testEmailProcessing() {
        safeVaultUserService.processUserEmail( "6637f08d9f73f13847de9da9","onwukalotachukwu210@gmail.com");

        assertTrue(safeVaultUserService.isMethodEffectOccurred());
    }

    @Test
    void testEmailProcessing_ThrowExceptionWhenEmailIsInvalid() {
        assertThrows(SafeVaultException.class, () -> safeVaultUserService.processUserEmail( "661908db349dd6078964982c","onwukalotachukwu@"));
    }

    @Test
    void testEmailProcessing_ThrowExceptionWhenEmailIsNull() {
        assertThrows(SafeVaultException.class, () -> safeVaultUserService.processUserEmail("", "661908db349dd6078964982c"));
    }

    @Test
    void testSetPasscode(){

        String response = safeVaultUserService.setPasscode("6637f08d9f73f13847de9da9", "121323", "121323");
        assertThat(response, is("Passcode set successfully, continue"));
    }

    @Test
    void testSetPasscode_ThrowExceptionIfPasscodesDoesNotMatch(){

        SafeVaultException exception = assertThrows(SafeVaultException.class, ()->safeVaultUserService.setPasscode("661908db349dd6078964982c", "121323", "12132324444"));
        assertThat(exception.getMessage(), is("Passcodes must match"));
    }

    @Test
    void testViewBankCustomerDetails(){
        UserResponseDto responseDto = safeVaultUserService.viewCustomer("661908db349dd6078964982c");

        assertThat(responseDto.getEmail(), is("onwukalotachukwu210@gmail.com"));
    }

    @Test
    void testViewAllBankCustomerDetails(){
        List<UserResponseDto> responseDto = safeVaultUserService.viewAllCustomers(1, 10);

        assertThat(responseDto.size(), is(2));
    }
}
