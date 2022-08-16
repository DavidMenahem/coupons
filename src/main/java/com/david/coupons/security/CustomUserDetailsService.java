package com.david.coupons.security;
import com.david.coupons.entities.CompanyEntity;
import com.david.coupons.entities.CustomerEntity;
import com.david.coupons.enums.Role;
import com.david.coupons.exceptions.ApplicationException;
import com.david.coupons.services.CompanyService;
import com.david.coupons.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.david.coupons.constants.TestData.ADMIN_LOGIN_EMAIL;
import static com.david.coupons.constants.TestData.ADMIN_LOGIN_PASSWORD;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final CompanyService companyService;
    private final CustomerService customerService;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        if(username.equals(ADMIN_LOGIN_EMAIL)) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(String.valueOf(Role.Admin)));
            user = new User(ADMIN_LOGIN_EMAIL, ADMIN_LOGIN_PASSWORD, authorities);
        } else{
            CompanyEntity company = companyService.getByEmail(username);
            if(company != null) {
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(String.valueOf(Role.Company)));
                user = new User(company.getEmail(), String.valueOf(company.getPassword()), authorities);
            }else {
                CustomerEntity customer = customerService.getByEmail(username);
                if(customer !=null) {
                    List<GrantedAuthority> authorities = new ArrayList<>();
                    authorities.add(new SimpleGrantedAuthority(String.valueOf(Role.Customer)));
                    user = new User(customer.getEmail(), String.valueOf(customer.getPassword()),authorities);
                }
            }
        }
        Optional<User> loggedInUser = Optional.of(user);
        if(loggedInUser.isPresent()){
            return user;
        }else{
            throw new UsernameNotFoundException("No such user exists");
        }
    }
}
