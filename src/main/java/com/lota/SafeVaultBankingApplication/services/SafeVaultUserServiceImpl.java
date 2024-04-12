package com.lota.SafeVaultBankingApplication.services;

import com.lota.SafeVaultBankingApplication.config.SmsSender;
import com.lota.SafeVaultBankingApplication.exceptions.AppException;
import com.lota.SafeVaultBankingApplication.exceptions.SuccessMessage;
import com.lota.SafeVaultBankingApplication.models.SafeVaultUser;
import com.lota.SafeVaultBankingApplication.repositories.SafeVaultUserRepository;
import com.lota.SafeVaultBankingApplication.security.models.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static com.lota.SafeVaultBankingApplication.exceptions.ExceptionMessages.*;
import static com.lota.SafeVaultBankingApplication.exceptions.SuccessMessage.OTP_VERIFIED_SUCCESSFULLY;

@Service
@RequiredArgsConstructor
@Slf4j
public class SafeVaultUserServiceImpl implements SafeVaultUserService, UserDetailsService {

    private final SafeVaultUserRepository userRepository;
    private final SmsSender smsSender;
    @Override
    public SafeVaultUser getUserBy(String searchParam) {
        return findUserByEmail(searchParam);
    }


    private SafeVaultUser findUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND.getMessage(), email)
                ));
    }

    private SafeVaultUser findUserById(String id){
        return userRepository.findById(id)
                .orElseThrow(()-> new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND.getMessage(), id)
                ));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SafeVaultUser user = getUserBy(username);
        return new Principal(user);
    }

    @Override
    public void processUserPhoneNumber(String  phoneNumber) {
        boolean isPhoneNumberExists = userRepository.existsByPhoneNumber(phoneNumber);

        if (isPhoneNumberExists) throw new AppException(ACCOUNT_ALREADY_EXISTS.getMessage());

        smsSender.sendSmsTo(phoneNumber);
        String userOtp = getUserOtp();

        SafeVaultUser user = SafeVaultUser.builder().phoneNumber(phoneNumber)
                .otp(userOtp).otpCreatedTime(LocalDateTime.now()).build();
        userRepository.save(user);
    }


    @Override
    public String validateOtp(String otp, String userId) {

        SafeVaultUser safeVaultUser = findUserById(userId);
        if (safeVaultUser.getOtp().isEmpty()) throw new AppException(OTP_NULL.getMessage());

        final long timeElapsed = ChronoUnit.MINUTES.between(safeVaultUser.getOtpCreatedTime(), LocalDateTime.now());

        if(timeElapsed > 10) throw new AppException(OTP_EXPIRED.getMessage());

        if(!safeVaultUser.getOtp().matches(otp)) throw new AppException(INCORRECT_OTP.getMessage());

        safeVaultUser.setOtpVerified(true);
        userRepository.save(safeVaultUser);
        return OTP_VERIFIED_SUCCESSFULLY.getMessage();
    }

    private String getUserOtp(){
        return smsSender.generateToken();
    }

    @Override
    public String regenerateOtp(String userId){
        SafeVaultUser user = findUserById(userId);
        String otp = smsSender.generateToken();
        user.setOtp(otp);
        userRepository.save(user);

        return otp;

    }

    private void validatePhoneNumberLength(String phoneNumber){
//        String regex = "((^+)(234){1}[0–9]{10})|((^234)[0–9]{10})|((^0)(7|8|9){1}(0|1){1}[0–9]{8})";
//        if (!phoneNumber.matches(regex)) throw new AppException(INVALID_PHONENUMBER.toString());

        if(phoneNumber.length() != 11) throw new AppException("Invalid phone number");

    }
    @Override
    public void processUserEmail(String email, String userId) {
        SafeVaultUser user =findUserById(userId);
        validateEmail(email);
        user.setEmail(email);
        userRepository.save(user);
    }

    private void validateEmail(String email){
        String regex ="^(.+)@(\\S+)$";
        if (!email.matches(regex)) throw new AppException(INCORRECT_EMAIL.toString());
     }


}
