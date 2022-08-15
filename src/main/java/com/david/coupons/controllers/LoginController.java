package com.david.coupons.controllers;

import com.david.coupons.entities.Credentials;
import com.david.coupons.exceptions.ApplicationException;
import com.david.coupons.services.AuthService;
import com.david.coupons.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final AuthService authService;

    @PostMapping
    public String login(@RequestBody Credentials credentials) throws ApplicationException {

        return authService.login(credentials);
    }
}
