package com.project.garden.core.otp;

import com.kavenegar.sdk.KavenegarApi;
import com.kavenegar.sdk.excepctions.ApiException;
import com.kavenegar.sdk.excepctions.HttpException;
import com.project.garden.app.phone.model.Phone;
import com.project.garden.app.phone.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class SmsHandler {

    @Value("${modules.sms-handler.api-key}")
    private String smsApiKey;
    @Value("${modules.sms-handler.sender}")
    private String phoneNumber;
    private KavenegarApi api;
    @Autowired
    private Environment env;
    @Autowired
    private PhoneService phoneService;

    private final Logger logger = Logger.getLogger(SmsHandler.class.getSimpleName());

    public void sendOTPCode(Phone phone, int OTPCode) {
        sendMessage(phone.getNumber(), String.valueOf(OTPCode));
    }

    private void sendMessage(String phone, String token) throws HttpException, ApiException {
        this.api = new KavenegarApi(smsApiKey);
        api.verifyLookup(phone, token, "verification");
    }
}
