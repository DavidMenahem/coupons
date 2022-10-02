package com.david.coupons.controllers;

import com.david.coupons.entities.CompanyEntity;
import com.david.coupons.entities.CustomerEntity;
import com.david.coupons.exceptions.ApplicationException;
import com.david.coupons.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping("/register")
@RestController
@RequiredArgsConstructor
public class RegisterController {
    private final AdminService adminService;
    @PostMapping("/company")
    public void registerCompany(@RequestBody CompanyEntity company) throws ApplicationException {
        adminService.createCompany(company);
    }

    @PostMapping("/customer")
    public void registerCustomer(@RequestBody CustomerEntity customer) throws ApplicationException {
        adminService.createCustomer(customer);
    }
}
