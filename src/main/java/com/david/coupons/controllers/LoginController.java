package com.david.coupons.controllers;

import com.david.coupons.entities.Credentials;
import com.david.coupons.exceptions.ApplicationException;
import com.david.coupons.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final AuthService authService;

    @PostMapping
    public String login(@RequestBody final Credentials credentials) throws ApplicationException {
        try {
            return authService.login(credentials);
        }catch(Exception e){
            throw new ApplicationException("Error logging in");
        }
    }
}
