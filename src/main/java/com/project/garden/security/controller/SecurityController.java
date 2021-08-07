package com.project.garden.security.controller;

import com.project.garden.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/security")
public class SecurityController {

    private final SecurityService securityService;
    @Autowired
    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping("/send-code")
    public ResponseEntity<Void> sendCode(@RequestParam String phone) {
        securityService.sendCode(phone);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/admin-login")
    public ResponseEntity<String> adminLogin(@RequestParam String phone, @RequestParam int code) {
        return ResponseEntity.ok(securityService.adminLogin(phone, code));
    }

    @PostMapping("/customer-login")
    public ResponseEntity<String> customerLogin(@RequestParam String phone, @RequestParam int code) {
        return ResponseEntity.ok(securityService.customerLogin(phone, code));
    }

    @PostMapping("/gate-login")
    public ResponseEntity<String> gateLogin(@RequestParam String phone, @RequestParam int code) {
        return ResponseEntity.ok(securityService.gateLogin(phone, code));
    }
}
