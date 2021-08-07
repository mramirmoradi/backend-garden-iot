package com.project.garden.security.service;

public interface SecurityService {

    public void sendCode (String phoneNumber);

    //ADMIN
    public String adminLogin (String phoneNumber, int code);


    //CUSTOMER
    public String customerLogin (String phoneNumber, int code);

    //GATE
    public String gateLogin (String phoneNumber, int code);
}
