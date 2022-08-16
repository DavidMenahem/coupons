package com.david.coupons.services;

import com.david.coupons.entities.CompanyEntity;
import com.david.coupons.entities.CustomerEntity;
import com.david.coupons.entities.CouponEntity;
import com.david.coupons.exceptions.ApplicationException;
import com.david.coupons.repositories.CompanyRepository;
import com.david.coupons.repositories.CouponRepository;
import com.david.coupons.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Transactional
@Service
@RequiredArgsConstructor
public class AdminService {
    private final CompanyRepository companyRepository;
    private final CouponRepository couponRepository;
    private final CustomerRepository customerRepository;

    //Companies
    public CompanyEntity createCompany(final CompanyEntity companyEntity) throws ApplicationException {
        List<CompanyEntity> companyEntities = companyRepository.findAll();
        boolean emailExists = false;
        boolean nameExists = false;
        for (CompanyEntity company : companyEntities) {
            if (company.getName().equals(companyEntity.getName())) {
                nameExists = true;
                break;
            }
            if (company.getEmail().equals(companyEntity.getEmail())) {
                emailExists = true;
                break;
            }
        }
        if (emailExists) {
            throw new ApplicationException("Email already exists " + companyEntity.getEmail());
        } else if (nameExists) {
            throw new ApplicationException("Name already exists " + companyEntity.getName());
        } else {
            companyEntity.setPassword(String.valueOf(companyEntity.getPassword()).hashCode());
            return companyRepository.save(companyEntity);
        }

    }
    public CompanyEntity updateCompany(final CompanyEntity companyEntity) throws ApplicationException {
        boolean emailExists = false;
        Optional<CompanyEntity> companyEntityFromDB = companyRepository.findById(companyEntity.getId());
        if(companyEntityFromDB.isPresent()) {

                companyEntity.setName(companyEntityFromDB.get().getName());
                List<CompanyEntity> companyEntities = companyRepository.findAll();

                for (CompanyEntity company : companyEntities) {
                    if (company.getId() == companyEntity.getId()) {
                        continue;
                    }
                    if (company.getEmail().equals(companyEntity.getEmail())) {
                        emailExists = true;
                        break;
                    }
                }
        }
        if (emailExists) {
            throw new ApplicationException("Email already exists" + companyEntity.getEmail());
        } else {
            return companyRepository.save(companyEntity);
        }
    }

    public CompanyEntity updateCompanyDetails(final CompanyEntity companyEntity) throws ApplicationException {
        boolean emailExists = false;
        Optional<CompanyEntity> companyEntityFromDB = companyRepository.findById(companyEntity.getId());
        if(companyEntityFromDB.isPresent()) {

            companyEntity.setName(companyEntityFromDB.get().getName());
            List<CompanyEntity> companyEntities = companyRepository.findAll();

            for (CompanyEntity company : companyEntities) {
                if (company.getId() == companyEntity.getId()) {
                    continue;
                }
                if (company.getEmail().equals(companyEntity.getEmail())) {
                    emailExists = true;
                    break;
                }
            }
        }
        if (emailExists) {
            throw new ApplicationException("Email already exists" + companyEntity.getEmail());
        } else {
            if(companyEntityFromDB.isPresent()) {
                companyEntity.setPassword(companyEntityFromDB.get().getPassword());
                return companyRepository.save(companyEntity);
            }
                throw new ApplicationException("Failed to retrieve company + " + companyEntity.getName() +"\"from data base with id");
        }
    }

    public CompanyEntity updateCompanyPassword(final CompanyEntity company) throws ApplicationException {
        Optional<CompanyEntity> companyEntityFromDB = companyRepository.findById(company.getId());
        if(companyEntityFromDB.isPresent()) {
            companyEntityFromDB.get().setPassword(String.valueOf(company.getPassword()).hashCode());
            return companyRepository.save(companyEntityFromDB.get());
        }
            throw new ApplicationException("Failed to retrieve company :\"" + company.getName() + "\" from the database");

    }

    public void deleteCompany(final long companyId){

        List<CouponEntity> coupons = couponRepository.getByCompanyId(companyId);
        for (CouponEntity coupon: coupons){
            Set<CustomerEntity> customers = coupon.getCustomers();
            for(CustomerEntity customer: customers){
                coupon.removeCustomer(customer);
            }
        }
        couponRepository.deleteByCompanyId(companyId);
    }
    public CompanyEntity getCompanyById(final long companyId) throws ApplicationException {
        Optional<CompanyEntity> company = companyRepository.findById(companyId);
        if(company.isPresent()){
            return company.get();
        }
            throw new ApplicationException("Failed to retrieve company from the database with id " + companyId);
    }

    public List<CompanyEntity> getAllCompanies(){
        return companyRepository.findAll();
    }

    //Customers
    public CustomerEntity createCustomer(final CustomerEntity customerEntity) throws ApplicationException {
        List<CustomerEntity> customerEntities = customerRepository.findAll();
        boolean emailExists = false;
        for (CustomerEntity customer: customerEntities){
            if(customer.getEmail().equals(customerEntity.getEmail())){
                emailExists = true;
                break;
            }
        }
        if(!emailExists) {
            customerEntity.setPassword(String.valueOf(customerEntity.getPassword()).hashCode());
            return customerRepository.save(customerEntity);
        }
            throw new ApplicationException("Email already exist in the system.");
    }

    public CustomerEntity updateCustomer(final CustomerEntity customerEntity) throws ApplicationException {

        List<CustomerEntity> customerEntities = customerRepository.findAll();
        boolean emailExists = false;
        for (CustomerEntity customer: customerEntities){
            if(customer.getId() == customerEntity.getId()){
                continue;
            }
            if(customer.getEmail().equals(customerEntity.getEmail())){
                emailExists = true;
                break;
            }
        }
        if(!emailExists) {
            return customerRepository.save(customerEntity);
        }
            throw new ApplicationException("Email already exist in the system.");
    }
    public CustomerEntity updateCustomerDetails(final CustomerEntity customerEntity) throws ApplicationException {

        int password = 0;
        List<CustomerEntity> customerEntities = customerRepository.findAll();
        boolean emailExists = false;
        for (CustomerEntity customer: customerEntities){
            if(customer.getId() == customerEntity.getId()){
                password = customer.getPassword();
                continue;
            }
            if(customer.getEmail().equals(customerEntity.getEmail())){
                emailExists = true;
                break;
            }
        }
        if(!emailExists) {
            customerEntity.setPassword(password);
            return customerRepository.save(customerEntity);
        }
            throw new ApplicationException("Email already exist in the system.");
    }

    public CustomerEntity updateCustomerPassword(final CustomerEntity customer) throws ApplicationException {
        Optional<CustomerEntity> customerEntityFromDB = customerRepository.findById(customer.getId());
        if(customerEntityFromDB.isPresent()) {
            customerEntityFromDB.get().setPassword(String.valueOf(customer.getPassword()).hashCode());
        }
        if(customerEntityFromDB.isPresent()) {
            return customerRepository.save(customerEntityFromDB.get());
        }
            throw new ApplicationException("Failed to retrieve customer :\"" + customer.getFirstName() + " " + customer.getLastName() +  "\" from the database");

    }

    public void deleteCustomer(final long customerId){
        customerRepository.deleteById(customerId);
    }

    public CustomerEntity getCustomerById(final long customerId) throws ApplicationException {
        Optional<CustomerEntity> customer =  customerRepository.findById(customerId);
        if(customer.isPresent()){
            return customer.get();
        }
            throw new ApplicationException("Failed to retrieve customer from the database with id " + customerId);
    }

    public List<CustomerEntity> getAllCustomers(){
        return customerRepository.findAll();
    }

    public CustomerEntity getOneCustomer(final long customerId) throws ApplicationException {
        Optional<CustomerEntity> customer = customerRepository.findById(customerId);
        if(customer.isPresent()) {
            return customer.get();
        }
            throw new ApplicationException("Failed to retrieve customer from the database with id " + customerId);
    }
}
