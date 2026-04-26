package com.Ecommerce.App.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TwilioSmsService {

    public void sendSms(String toPhone, String message) {
        // SMS disabled for now — just log
        log.info("SMS to {}: {}", toPhone, message);
    }
}