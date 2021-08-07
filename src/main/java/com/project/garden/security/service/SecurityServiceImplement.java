package com.project.garden.security.service;

import com.project.garden.app.gate.model.Gate;
import com.project.garden.app.gate.service.GateService;
import com.project.garden.app.phone.model.Phone;
import com.project.garden.app.phone.service.PhoneService;
import com.project.garden.core.exception.Error;
import com.project.garden.core.exception.ErrorStatus;
import com.project.garden.core.otp.OTPService;
import com.project.garden.core.otp.SmsHandler;
import com.project.garden.security.user.model.Role;
import com.project.garden.security.user.model.User;
import com.project.garden.security.user.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class SecurityServiceImplement implements SecurityService{

    private final PhoneService phoneService;
    private final UserService userService;
    private final GateService gateService;
    private final OTPService otpService;
    private final SmsHandler smsHandler;
    private final Environment environment;
    private final AuthenticationManager authenticationManager;
    @Autowired
    public SecurityServiceImplement(PhoneService phoneService,
                                    UserService userService,
                                    GateService gateService, OTPService otpService, SmsHandler smsHandler, Environment environment, AuthenticationManager authenticationManager) {
        this.phoneService = phoneService;
        this.userService = userService;
        this.gateService = gateService;
        this.otpService = otpService;
        this.smsHandler = smsHandler;
        this.environment = environment;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void sendCode(String phoneNumber) {
        validatePhone(phoneNumber);
        Phone phone = phoneService.findByNumber(phoneNumber);
        smsHandler.sendOTPCode(phone, otpService.generateOTP(phone));
    }

    @Override
    public String adminLogin(String phoneNumber, int code) {
        Phone phone = phoneService.findByNumber(phoneNumber);
        codeExpirationValidation(phone);
        validateAdmin(phone);
        return login(phone, code);
    }

    @Override
    public String customerLogin(String phoneNumber, int code) {
        Phone phone = phoneService.findByNumber(phoneNumber);
        codeExpirationValidation(phone);
        validateCustomer(phone);
        return login(phone, code);
    }

    @Override
    public String gateLogin(String phoneNumber, int code) {
        Phone phone = phoneService.findByNumber(phoneNumber);
        codeExpirationValidation(phone);
        if (otpService.getOTP(phone) == code) {
            Gate gate = gateService.findByPhone(phone);
            return gateTokenGenerator(gate);
        } else
            throw new Error(ErrorStatus.INVALID_OTP_CODE, Phone.class.getSimpleName(), "the code is not valid", null);
    }

    //PRIVATE METHODS ----------------------------------------------------------------------------------------------------------------------------------

    private String login(Phone phone, int code) {
        if (otpService.getOTP(phone) == code){
            User user = userService.findByPhone(phone);
            return tokenGenerator(user);
        }
        else
            throw new Error(ErrorStatus.INVALID_OTP_CODE, Phone.class.getSimpleName(), "the code is not valid", null);
    }

    private String tokenGenerator(User user) {
        Map<String, Object> claims = new HashMap<>();
        List<String> permissions = new ArrayList<>();
        permissions.add(user.getRole().name());
        claims.put("role", user.getRole().name());
        return environment.getProperty("security.token.prefix") + " " +  Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(environment.getProperty("security.secret.key").getBytes()), SignatureAlgorithm.HS512)
                .setSubject(String.valueOf(user.getId()))
                .setHeaderParam("type", environment.getProperty("security.token.type"))
                .setIssuer(environment.getProperty("security.token.issuer"))
                .setAudience(environment.getProperty("security.token.audience"))
                .addClaims(claims)
                .claim("PERMISSIONS", permissions)
                .compact();
    }

    private String gateTokenGenerator(Gate gate) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("gateId", String.valueOf(gate.getId()));
        claims.put("role", gate.getUser().getRole().toString());
        List<String> permissions = new ArrayList<>();
        permissions.add(gate.getUser().getRole().name());
        return environment.getProperty("security.token.prefix") + " " + Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(environment.getProperty("security.secret.key").getBytes()), SignatureAlgorithm.HS512)
                .setHeaderParam("type", environment.getProperty("security.token.type"))
                .setIssuer(environment.getProperty("security.token.issuer"))
                .setAudience(environment.getProperty("security.token.audience"))
                .setSubject(String.valueOf(gate.getUser().getId()))
                .addClaims(claims)
                .claim("PERMISSIONS", permissions)
                .compact();
    }

    private void validatePhone (String phoneNumber) {
        if (!phoneService.isExist(phoneNumber))
            throw new Error(ErrorStatus.PHONE_NOTFOUND, Phone.class.getSimpleName(), "phone not registered in system", null);
    }

    private void validateAdmin(Phone phone) {
        User user = userService.findByPhone(phone);

        if (user.getRole() != Role.ADMIN)
            throw new Error(ErrorStatus.VALIDATION, User.class.getSimpleName(), "you dont have admin permission", null);
    }

    private void validateCustomer(Phone phone) {
        User user = userService.findByPhone(phone);

        if (user.getRole() != Role.CUSTOMER)
            throw new Error(ErrorStatus.VALIDATION, User.class.getSimpleName(), "you dont have customer permission", null);
    }

    private void codeExpirationValidation(Phone phone) {
        if (!otpService.isExist(phone))
            throw new Error(ErrorStatus.EXPIRE, Phone.class.getSimpleName(), "the code was expired", null);
    }
}
