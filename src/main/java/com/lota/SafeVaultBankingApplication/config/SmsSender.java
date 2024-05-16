package com.lota.SafeVaultBankingApplication.config;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Configuration
public class SmsSender {

    @Value("${twillo.sid}")
    private  String  ACCOUNT_SID;

    @Value("${twillo.key}")
    private String AUTH_TOKEN;

    public String generateToken() {
        Random random = new Random();

        String randomNumber = IntStream.range(0, 6)
                .mapToObj(i -> String.valueOf(random.nextInt(10)))
                .collect(Collectors.joining());

        System.out.println("Random Number: " + randomNumber);

        return randomNumber;
    }

    public  void sendSmsTo(String phoneNumber){
        log.info("ACCOUNT SID ------> {}", ACCOUNT_SID);
        log.info("AUTH TOKEN ------> {}", AUTH_TOKEN);
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        String token = generateToken();

        // Send the token via SMS
        Message message = Message.creator(
                        new PhoneNumber(phoneNumber),
                        new PhoneNumber("+13343578207"),
                        token)
                .create();

        System.out.println("Message sent successfully. SID: " + message.getSid());
    }
}

