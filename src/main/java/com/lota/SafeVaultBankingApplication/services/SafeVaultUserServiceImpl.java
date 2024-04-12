package com.lota.SafeVaultBankingApplication.services;

import com.lota.SafeVaultBankingApplication.config.SmsSender;
import com.lota.SafeVaultBankingApplication.exceptions.AppException;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SafeVaultUser user = getUserBy(username);
        return new Principal(user);
    }

    @Override
    public void processUserPhoneNumber(String  phoneNumber) {
        boolean isPhoneNumberExists = userRepository.existsByPhoneNumber(phoneNumber);

        if (isPhoneNumberExists) throw new AppException(ACCOUNT_ALREADY_EXISTS.toString());

        String userOtp = getUserOtp();
        smsSender.sendSmsTo(phoneNumber);

        SafeVaultUser user = SafeVaultUser.builder().phoneNumber(phoneNumber)
                .otp(userOtp).otpCreatedTime(LocalDateTime.now()).build();
        userRepository.save(user);
    }

    @Override
    public String validateOtp(String otp, String userId) {

        SafeVaultUser safeVaultUser = getUserBy(userId);
        if (safeVaultUser.getOtp().isEmpty()) throw new AppException("");

        final long timeElapsed = ChronoUnit.MINUTES.between(safeVaultUser.getOtpCreatedTime(), LocalDateTime.now());

        if(timeElapsed > 10) throw new AppException("");

        if(!safeVaultUser.getOtp().matches(otp)) throw new AppException("");

        safeVaultUser.setOtpVerified(true);
        userRepository.save(safeVaultUser);
        return "OTP VERIFIED SUCCESSFULLY";
    }

    private String getUserOtp(){
        return smsSender.generateToken();
    }

    private void validatePhoneNumberLength(String phoneNumber){
//        String regex = "((^+)(234){1}[0–9]{10})|((^234)[0–9]{10})|((^0)(7|8|9){1}(0|1){1}[0–9]{8})";
//        if (!phoneNumber.matches(regex)) throw new AppException(INVALID_PHONENUMBER.toString());

        if(phoneNumber.length() != 11) throw new AppException("Invalid phone number");

    }

    private void validateEmail(String email){
        String regex ="^(.+)@(\\S+)$";
        if (!email.matches(regex)) throw new AppException(INCORRECT_EMAIL.toString());
     }


//    @Override
//    public void registerUser(RegisterRequest request) {
//
//        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) throw new AppException("An account already exists with this phone number");
//
//        SafeVaultUser user = new SafeVaultUser();
////        validatePhoneNumber(request.getPhoneNumber());
//        smsSender.sendSmsTo(request.getPhoneNumber());
//
//        if(!Objects.equals(request.getPasscode(), request.getConfirmPasscode())) throw new AppException("Passwords does not match");
//        validateEmail(request.getEmail());
//
//        user.setEmail(request.getEmail());
//        user.setPhoneNumber(request.getPhoneNumber());
//        user.setPassword(request.getPasscode());
//        user.setDateCreated(LocalDateTime.now());
//
//        userRepository.save(user);
//    }

}
