package com.lota.SafeVaultBankingApplication.config;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Value;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SmsSender {

    @Value("${twillo.sid}")
    private static String  ACCOUNT_SID;

    @Value("${twillo.key}")
    private static String AUTH_TOKEN;
    // Your Twilio Account SID and Auth Token


    private static String generateToken() {
        Random random = new Random();

        String randomNumber = IntStream.range(0, 6)
                .mapToObj(i -> String.valueOf(random.nextInt(10)))
                .collect(Collectors.joining());

        System.out.println("Random Number: " + randomNumber);

        return randomNumber;
    }

    public static void sendSmsTo(String phoneNumber){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        String token = generateToken();

        // Send the token via SMS
        Message message = Message.creator(
                        new PhoneNumber(phoneNumber),
                        new PhoneNumber("+13343578207"),
                        "Here is me testing my app and also reminding you that lota loves you so much, enjoy the rest of your day dee: " + token)
                .create();

        System.out.println("Message sent successfully. SID: " + message.getSid());
    }
}

