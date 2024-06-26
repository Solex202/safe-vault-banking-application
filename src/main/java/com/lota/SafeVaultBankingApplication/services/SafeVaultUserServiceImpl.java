package com.lota.SafeVaultBankingApplication.services;

import com.lota.SafeVaultBankingApplication.config.SmsSender;
import com.lota.SafeVaultBankingApplication.dtos.request.SetPasscodeDto;
import com.lota.SafeVaultBankingApplication.dtos.response.PaginatedResponse;
import com.lota.SafeVaultBankingApplication.dtos.response.UserResponseDto;
import com.lota.SafeVaultBankingApplication.exceptions.SafeVaultException;
import com.lota.SafeVaultBankingApplication.models.SafeVaultUser;
import com.lota.SafeVaultBankingApplication.repositories.AccountRepository;
import com.lota.SafeVaultBankingApplication.repositories.SafeVaultUserRepository;
import com.lota.SafeVaultBankingApplication.security.models.Principal;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.lota.SafeVaultBankingApplication.exceptions.ExceptionMessages.*;
import static com.lota.SafeVaultBankingApplication.exceptions.SuccessMessage.*;
import static com.lota.SafeVaultBankingApplication.utils.AppUtil.paginateDataWith;

@Service
@RequiredArgsConstructor
@Slf4j
public class SafeVaultUserServiceImpl implements SafeVaultUserService, UserDetailsService {

    private final SafeVaultUserRepository userRepository;
    private final SmsSender smsSender;
    private final ModelMapper mapper;
    private final AccountRepository accountRepository;

    @Getter
    private boolean methodEffectOccurred;
    @Override
    public SafeVaultUser getUserBy(String searchParam) {
        return findUserByEmail(searchParam);
    }

    //TODO:THOUGHT? What if a user doesn't complete a registration flow and some of the user's data has been saved on the system

    public SafeVaultUser findUserByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND.getMessage(), email)
                ));
    }

    @Override
    public SafeVaultUser findUserById(String id){
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
        if(phoneNumber.isEmpty()) throw new SafeVaultException(PHONE_NUMBER_IS_NULL.getMessage());
//
//        boolean isPhoneNumberExists = userRepository.existsByPhoneNumber(phoneNumber);
//        if (isPhoneNumberExists) throw new AppException(ACCOUNT_ALREADY_EXISTS.getMessage());

        smsSender.sendSmsTo(phoneNumber);
        String userOtp = getUserOtp();

        SafeVaultUser user = SafeVaultUser.builder().phoneNumber(phoneNumber)
                .otp(userOtp).otpCreatedTime(LocalDateTime.now()).build();
        userRepository.save(user);

        methodEffectOccurred = true;
    }


    @Override
    public String validateOtp(String userId,String otp) {
        SafeVaultUser safeVaultUser = findUserById(userId);

        if (safeVaultUser.isOtpVerified()) {
            throw new SafeVaultException(OTP_ALREADY_VERIFIED.getMessage());
        }

        String userOtp = safeVaultUser.getOtp();
        if (userOtp.isEmpty()) {
            throw new SafeVaultException(OTP_NULL.getMessage());
        }

        LocalDateTime otpCreationTime = safeVaultUser.getOtpCreatedTime();
        long timeElapsed = ChronoUnit.MINUTES.between(otpCreationTime, LocalDateTime.now());
        log.info("TIME ELAPSED -----> {}", timeElapsed);

        if (timeElapsed > 10) {
            throw new SafeVaultException(OTP_EXPIRED.getMessage());
        }

        if (!userOtp.equals(otp)) {
            throw new SafeVaultException(INCORRECT_OTP.getMessage());
        }

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
        user.setOtpCreatedTime(LocalDateTime.now(ZoneOffset.UTC));
        userRepository.save(user);

        log.info(OTP_RESENT_SUCCESSFULLY.getMessage());

        return OTP_RESENT_SUCCESSFULLY.getMessage();

    }

    private void validatePhoneNumberLength(String phoneNumber){
//        String regex = "((^+)(234){1}[0–9]{10})|((^234)[0–9]{10})|((^0)(7|8|9){1}(0|1){1}[0–9]{8})";
//        if (!phoneNumber.matches(regex)) throw new AppException(INVALID_PHONENUMBER.toString());

        if(phoneNumber.length() != 11) throw new SafeVaultException(INVALID_PHONENUMBER.getMessage());

    }
    @Override
    public void processUserEmail(String userId,String email) {
        SafeVaultUser user =findUserById(userId);
        validateEmail(email);
        user.setEmail(email);
        userRepository.save(user);

        methodEffectOccurred = true;
    }

    private void validateEmail(String email){
        String regex ="^(.+)@(\\S+)$";
        if (email.isEmpty()) throw new SafeVaultException(EMAIL_IS_NULL.getMessage());
        if (!email.matches(regex)) throw new SafeVaultException(INCORRECT_EMAIL.getMessage());
    }

    private boolean containsOnlyNumbers(String str) {
        return str.matches("[0-9]+");
    }

    @Override
    public String setPasscode(String userId, SetPasscodeDto passcodeDto) {

        SafeVaultUser user = findUserById(userId);

        validatePasscode(passcodeDto.getPasscode(), passcodeDto.getConfirmPasscode());

        String hashedPassword = hashPassword(passcodeDto.getPasscode());
        user.setPasscode(hashedPassword);

        userRepository.save(user);
        return PASSCODE_SET_SUCCESSFULLY.getMessage();
    }

    private void validatePasscode(String passcode, String confirmPasscode) {
        if(passcode.isEmpty() || confirmPasscode.isEmpty()) throw new SafeVaultException(PASSCODE_IS_NULL.getMessage());
        if (!containsOnlyNumbers(passcode) || !containsOnlyNumbers(confirmPasscode)) throw new SafeVaultException(INVALID_PASSCODE.getMessage());
        if(passcode.length() != 6 || confirmPasscode.length() != 6) throw new SafeVaultException(INVALID_PASSCODE_LENGTH.getMessage());
        if (!passcode.matches(confirmPasscode)) throw new SafeVaultException(PASSCODES_DO_NOT_MATCH.getMessage());
    }

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Verify a password using BCrypt
    public static boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    @Override
    public UserResponseDto viewCustomer(String userId){
        SafeVaultUser user = findUserById(userId);
        return mapper.map(user, UserResponseDto.class);
    }

    @Override
    public Page<UserResponseDto> viewAllCustomers(int page, int size){
        Pageable pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "id");

        Page<SafeVaultUser> userList = userRepository.findAll(pageRequest);
        List<UserResponseDto> userResponseDtos = buildCustomerResponseFrom(userList.getContent());

        return new PageImpl<>(userResponseDtos, pageRequest, userList.getTotalElements());

    }

    private List<UserResponseDto> buildCustomerResponseFrom(List<SafeVaultUser> userList) {

       return userList
               .stream()
               .map(user -> mapper.map(user, UserResponseDto.class))
               .toList();
    }

}
