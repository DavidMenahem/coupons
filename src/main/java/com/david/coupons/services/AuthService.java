package com.david.coupons.services;

import com.david.coupons.entities.Credentials;
import com.david.coupons.exceptions.ApplicationException;
import com.david.coupons.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final CompanyService companyService;
    private final CustomerService customerService;

    public String login(final Credentials credentials) throws ApplicationException {
        long id = 0;
        try {
            switch(credentials.getRole()){
                case Admin:
                    authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword()));
                        break;
                case Company:
                    id = companyService.getByEmail(credentials.getEmail()).getId();
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(credentials.getEmail(),
                                    credentials.getPassword().hashCode()));
                        break;
                case Customer:
                    id = customerService.getByEmail(credentials.getEmail()).getId();
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(credentials.getEmail(),
                                    credentials.getPassword().hashCode()));
                        break;
            }
        } catch (final BadCredentialsException e) {
            throw new ApplicationException("Incorrect credentials");
        }

        return JwtUtil.generateToken(credentials.getEmail(),credentials.getRole(),id);
    }
}
