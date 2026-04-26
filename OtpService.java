package com.Ecommerce.App.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpService {

    private final RedisTemplate<String, String> redisTemplate;

    // removed TwilioSmsService dependency
    // private final TwilioSmsService twilioSmsService;

    private static final String OTP_PREFIX = "OTP:";
    private static final long   OTP_EXPIRY = 5;

    public void sendOtp(String phone) {

        String otp = String.valueOf(
                new Random().nextInt(900000) + 100000);

        // store in Redis
        redisTemplate.opsForValue().set(
                OTP_PREFIX + phone,
                otp,
                OTP_EXPIRY,
                TimeUnit.MINUTES
        );

        // print OTP in console
        log.info("==============================");
        log.info("OTP for {} is: {}", phone, otp);
        log.info("==============================");

        // SMS disabled for now
        // twilioSmsService.sendSms(phone, "OTP: " + otp);
    }

    public boolean verifyOtp(String phone,
                             String enteredOtp) {

        String storedOtp = redisTemplate
                .opsForValue()
                .get(OTP_PREFIX + phone);

        if (storedOtp != null &&
                storedOtp.equals(enteredOtp)) {
            redisTemplate.delete(OTP_PREFIX + phone);
            return true;
        }
        return false;
    }
}