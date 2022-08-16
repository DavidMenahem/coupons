package com.david.coupons.controllers;

import com.david.coupons.entities.CompanyEntity;
import com.david.coupons.entities.CustomerEntity;
import com.david.coupons.exceptions.ApplicationException;
import com.david.coupons.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    //Companies
    @PostMapping("/companies")
    public CompanyEntity createCompany(@RequestBody final CompanyEntity companyEntity) throws ApplicationException {
        return adminService.createCompany(companyEntity);
    }
    @PutMapping("/companies/details/{companyId}")
    public CompanyEntity updateCompanyDetail(@PathVariable final long companyId,@RequestBody final CompanyEntity company) throws ApplicationException{
        company.setId(companyId);
        return adminService.updateCompanyDetails(company);
    }
    @PutMapping("/companies/password/{companyId}")
    public CompanyEntity updateCompanyPassword(@PathVariable final long companyId,@RequestBody final CompanyEntity company) throws ApplicationException {
        company.setId(companyId);
        return adminService.updateCompanyPassword(company);
    }
    @DeleteMapping("/companies/{companyId}")
    public void deleteCompany(@PathVariable final long companyId){
        adminService.deleteCompany(companyId);
    }
    @GetMapping("/companies")
    public List<CompanyEntity> getAllCompanies(){
        return adminService.getAllCompanies();
    }

    @GetMapping("/companies/{companyId}")
    public CompanyEntity getOneCompany(@PathVariable final long companyId) throws ApplicationException {
        return adminService.getCompanyById(companyId);
    }

    //Customers
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/customers")
    public CustomerEntity createCustomer(@RequestBody final CustomerEntity customerEntity) throws ApplicationException {
        return adminService.createCustomer(customerEntity);
    }
    @PutMapping("/customers/details/{customerId}")
    public CustomerEntity updateCustomerDetail(@PathVariable final long customerId,@RequestBody final CustomerEntity customerEntity) throws ApplicationException {
        customerEntity.setId(customerId);
        return adminService.updateCustomerDetails(customerEntity);
    }
    @PutMapping("/customers/password/{customerId}")
    public CustomerEntity updateCustomerPassword(@PathVariable final long customerId,@RequestBody final CustomerEntity customer) throws ApplicationException {
        customer.setId(customerId);
        return adminService.updateCustomerPassword(customer);
    }
    @DeleteMapping("/customers/{customerId}")
    public void deleteCustomer(@PathVariable final long customerId){
        adminService.deleteCustomer(customerId);
    }

    @GetMapping("/customers/{customerId}")
    public CustomerEntity getOneCustomer(@PathVariable final long customerId) throws ApplicationException {
        return adminService.getCustomerById(customerId);
    }
    @GetMapping("/customers")
    public List<CustomerEntity> getAllCustomers(){
        return adminService.getAllCustomers();
    }
}
