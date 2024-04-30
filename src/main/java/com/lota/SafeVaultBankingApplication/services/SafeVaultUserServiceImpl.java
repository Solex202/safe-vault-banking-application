package com.lota.SafeVaultBankingApplication.services;

import com.lota.SafeVaultBankingApplication.config.SmsSender;
import com.lota.SafeVaultBankingApplication.dtos.response.UserResponseDto;
import com.lota.SafeVaultBankingApplication.exceptions.AppException;
import com.lota.SafeVaultBankingApplication.models.SafeVaultUser;
import com.lota.SafeVaultBankingApplication.repositories.SafeVaultUserRepository;
import com.lota.SafeVaultBankingApplication.security.models.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    private boolean methodEffectOccurred;
    @Override
    public SafeVaultUser getUserBy(String searchParam) {
        return findUserByEmail(searchParam);
    }

    public boolean isMethodEffectOccurred() {
        return methodEffectOccurred;
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
        boolean isPhoneNumberExists = userRepository.existsByPhoneNumber(phoneNumber);

        if(phoneNumber.isEmpty()) throw new AppException(PHONE_NUMBER_IS_NULL.getMessage());
        if (isPhoneNumberExists) throw new AppException(ACCOUNT_ALREADY_EXISTS.getMessage());

        smsSender.sendSmsTo(phoneNumber);
        String userOtp = getUserOtp();

        SafeVaultUser user = SafeVaultUser.builder().phoneNumber(phoneNumber)
                .otp(userOtp).otpCreatedTime(LocalDateTime.now()).build();
        userRepository.save(user);

        methodEffectOccurred = true;
    }


    @Override
    public String validateOtp(String otp, String userId) {
        SafeVaultUser safeVaultUser = findUserById(userId);
        if(safeVaultUser.isOtpVerified()) throw new AppException(OTP_ALREADY_VERIFIED.getMessage());
        if (safeVaultUser.getOtp().isEmpty()) throw new AppException(OTP_NULL.getMessage());

        final long timeElapsed = ChronoUnit.MINUTES.between(safeVaultUser.getOtpCreatedTime(), LocalDateTime.now());
            log.info("TIME ELAPSED -----> {}", timeElapsed);
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
        user.setOtpCreatedTime(LocalDateTime.now(ZoneOffset.UTC));
        userRepository.save(user);

        log.info(OTP_RESENT_SUCCESSFULLY.getMessage());

        return OTP_RESENT_SUCCESSFULLY.getMessage();

    }

    private void validatePhoneNumberLength(String phoneNumber){
//        String regex = "((^+)(234){1}[0–9]{10})|((^234)[0–9]{10})|((^0)(7|8|9){1}(0|1){1}[0–9]{8})";
//        if (!phoneNumber.matches(regex)) throw new AppException(INVALID_PHONENUMBER.toString());

        if(phoneNumber.length() != 11) throw new AppException(INVALID_PHONENUMBER.getMessage());

    }
    @Override
    public void processUserEmail(String email, String userId) {
        SafeVaultUser user =findUserById(userId);
        validateEmail(email);
        user.setEmail(email);
        userRepository.save(user);

        methodEffectOccurred = true;
    }

    private void validateEmail(String email){
        String regex ="^(.+)@(\\S+)$";
        if (email.isEmpty()) throw new AppException(EMAIL_IS_NULL.getMessage());
        if (!email.matches(regex)) throw new AppException(INCORRECT_EMAIL.getMessage());
    }

    private boolean containsOnlyNumbers(String str) {
        return str.matches("[0-9]+");
    }

    @Override
    public String setPasscode(String userId, String passcode, String confirmPasscode) {

        SafeVaultUser user = findUserById(userId);

        validatePasscode(passcode, confirmPasscode);

        String hashedPassword = hashPassword(passcode);
        user.setPasscode(hashedPassword);

        userRepository.save(user);
        return PASSCODE_SET_SUCCESSFULLY.getMessage();
    }

    private void validatePasscode(String passcode, String confirmPasscode) {
        if(passcode.isEmpty() || confirmPasscode.isEmpty()) throw new AppException(PASSCODE_IS_NULL.getMessage());
        if (!containsOnlyNumbers(passcode) || !containsOnlyNumbers(confirmPasscode)) throw new AppException(INVALID_PASSCODE.getMessage());
        if(passcode.length() != 6 || confirmPasscode.length() != 6) throw new AppException(INVALID_PASSCODE_LENGTH.getMessage());
        if (!passcode.matches(confirmPasscode)) throw new AppException(PASSCODES_DO_NOT_MATCH.getMessage());
    }

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Verify a password using BCrypt
    public static boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    public void setAccountNumber(String userId){

        SafeVaultUser user = findUserById(userId);
        Random random = new Random();

        String randomNumber = IntStream.range(0, 7)
                .mapToObj(i -> String.valueOf(random.nextInt(10)))
                .collect(Collectors.joining());

        user.setAccountNumber(List.of(user.getPhoneNumber().substring(4), "031" + randomNumber));

        userRepository.save(user);
    }


    @Override
    public UserResponseDto viewCustomer(String userId){
        SafeVaultUser user = findUserById(userId);
        return mapper.map(user, UserResponseDto.class);
    }

    public List<UserResponseDto> viewAllCustomers(int page, int size){
        Pageable pageRequest = paginateDataWith(page, size);
        Page<SafeVaultUser> userList = userRepository.findAll(pageRequest);

        return buildCustomerResponseFrom(userList);
    }

    private List<UserResponseDto> buildCustomerResponseFrom(Page<SafeVaultUser> userList) {

       return userList.getContent()
               .stream()
               .map(user -> mapper.map(user, UserResponseDto.class))
               .toList();
    }
    //TODO: delete a user, return a string, necessary?

}
