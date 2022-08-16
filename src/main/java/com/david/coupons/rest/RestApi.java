package com.david.coupons.rest;

import com.david.coupons.constants.TestData;
import com.david.coupons.entities.CompanyEntity;
import com.david.coupons.entities.CouponEntity;
import com.david.coupons.entities.CustomerEntity;
import com.david.coupons.repositories.CompanyRepository;
import com.david.coupons.repositories.CouponRepository;
import com.david.coupons.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

//
@Component
@RequiredArgsConstructor
public class RestApi {

    private final AdminService adminService;
    private final CouponRepository couponRepository;

    private final CompanyRepository companyRepository;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public void run() {
//        //Companies
        if(addCompany()){
            System.out.println("Company created");
        }else{
            System.out.println("Failed to create company");
        }

        if(updateCompany()){
            System.out.println("Company updated");
        }else{
            System.out.println("Failed to update company");
        }

        if(getOneCompany()){
            System.out.println("Retrieved company");
        }else{
            System.out.println("Failed to retrieve company");
        }

        if(getAllCompanies()){
            System.out.println("Retrieved all companies");
        }else{
            System.out.println("Failed to retrieve all companies");
        }

        //customers
        if(addCustomer()){
            System.out.println("Customer created");
        }else{
            System.out.println("Failed to create customer");
        }

        if(updateCustomer()){
            System.out.println("Customer updated");
        }else{
            System.out.println("Failed to update customer");
        }

        if(getOneCustomer()){
            System.out.println("Retrieved customer");
        }else{
            System.out.println("Failed to retrieve customer");
        }
        if(getAllCustomers()){
            System.out.println("Retrieved all customers");
        }else{
            System.out.println("Failed to retrieve all customers");
        }

        //coupons
        if(addCoupon()){
            System.out.println("Coupon added");
        }else{
            System.out.println("Failed to add coupon");
        }

        if(updateCoupon()){
            System.out.println("Updated coupon");
        }else{
            System.out.println("Failed to update coupon");
        }

        if(getCompanyCoupons()){
            System.out.println("Retrieved company coupons");
        }else{
            System.out.println("Failed to retrieve company coupons");
        }

        if(getCompanyCouponsByCategory()){
            System.out.println("Retrieved company coupons by category");
        }else{
            System.out.println("Failed to retrieve company coupons by category");
        }
        if(getCompanyCouponsPriceLessThanMaxPrice()){
            System.out.println("Retrieved company coupons by max price");
        }else{
            System.out.println("Falied to retrieve company coupons by max price");
        }

        if(deleteCompany()){
            System.out.println("Company deleted");
        }else{
            System.out.println("Failed to delete company");
        }

      if(deleteCustomer()){
          System.out.println("Customer deleted");
       }else{
          System.out.println("Failed to delete customer");
       }

        if(deleteCoupon()){
            System.out.println("Coupon deleted");
        }else{
            System.out.println("Failed to delete coupon");
        }
    }
//
//
//    //Companies
    private boolean addCompany(){
        CompanyEntity company = CompanyEntity.builder()
                .name(TestData.COMPANY_NAME)
                .email(TestData.COMPANY_EMAIL)
                .password(TestData.COMPANY_PASSWORD.hashCode())
                .build();
        try{
            ResponseEntity<CompanyEntity> response = restTemplate().postForEntity(
                    "http://localhost:8080/admin/companies",company,CompanyEntity.class
            );

            CompanyEntity companyRes = response.getBody();
            if (companyRes != null) {
                return companyRes.getId() > 0;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    private boolean updateCompany(){
        CompanyEntity company = CompanyEntity.builder()
                        .id(TestData.COMPANY_ID)
                        .name(TestData.COMPANY_NAME)
                        .email(TestData.COMPANY_EMAIL_UPDATE)
                        .password(TestData.COMPANY_PASSWORD.hashCode())
                        .build();
        try{
            restTemplate().put(
                    "http://localhost:8080/admin/companies/" + company.getId(),
                    company,CompanyEntity.class
            );

            CompanyEntity companyFromDB = adminService.getCompanyById(company.getId());

            return company.getEmail().equals(companyFromDB.getEmail());
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteCompany(){
        try {
            restTemplate().delete("http://localhost:8080/admin/companies/" + 1);
            CompanyEntity companyEntity = adminService.getCompanyById(1);
            return companyEntity == null;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean getOneCompany(){
        ResponseEntity<CompanyEntity> response = restTemplate().getForEntity(
                "http://localhost:8080/admin/companies/" + TestData.COMPANY_ID,CompanyEntity.class
        );
        CompanyEntity company = response.getBody();
        return company != null;
    }
    public boolean getAllCompanies(){

        try{
            ResponseEntity<CompanyEntity[]> response = restTemplate().getForEntity(
                    "http://localhost:8080/admin/companies",CompanyEntity[].class
        );
            CompanyEntity[] companiesArray = response.getBody();
            return (companiesArray != null ? companiesArray.length : 0) > 0;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
//
//    //Customers
    private boolean addCustomer(){
        CustomerEntity customer = CustomerEntity.builder()
                        .firstName(TestData.CUSTOMER_FIRST_NAME)
                        .lastName(TestData.CUSTOMER_LAST_NAME)
                        .email(TestData.CUSTOMER_EMAIL)
                        .password(TestData.CUSTOMER_PASSWORD.hashCode())
                        .build();
        try{
            ResponseEntity<CustomerEntity> response = restTemplate().postForEntity(
                    "http://localhost:8080/admin/customers",customer,CustomerEntity.class
            );
            CustomerEntity customerRes = response.getBody();
            if (customerRes != null) {
                return customerRes.getId() > 0;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean updateCustomer() {
        CustomerEntity customer = CustomerEntity.builder()
                .id(TestData.CUSTOMER_ID)
                .firstName(TestData.CUSTOMER_FIRST_NAME_UPDATE)
                .lastName(TestData.CUSTOMER_LAST_NAME_UPDATE)
                .email(TestData.CUSTOMER_EMAIL_UPDATE)
                .password(TestData.CUSTOMER_PASSWORD.hashCode())
                .build();
        try {
            restTemplate().put(
                    "http://localhost:8080/admin/customers/" + customer.getId(), customer, CustomerEntity.class
            );
            CustomerEntity customerFromDB = adminService.getCustomerById(TestData.CUSTOMER_ID);

            return customerFromDB.getEmail().equals(customer.getEmail());
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteCustomer() {
        try {
            restTemplate().delete("http://localhost:8080/admin/customers/" + TestData.CUSTOMER_ID);

            CustomerEntity customer = adminService.getCustomerById(TestData.CUSTOMER_ID);
            return customer == null;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean getOneCustomer(){
        ResponseEntity<CustomerEntity> response = restTemplate().getForEntity(
                "http://localhost:8080/admin/customers/" + TestData.CUSTOMER_ID,CustomerEntity.class
        );
        CustomerEntity customer = response.getBody();
        return customer != null;
    }
    public boolean getAllCustomers(){
        ResponseEntity<CustomerEntity[]> response = restTemplate().getForEntity(
                "http://localhost:8080/admin/customers/",CustomerEntity[].class
        );

        CustomerEntity[] customerArray = response.getBody();
        if (customerArray != null) {
            return customerArray.length > 0;
        }
        return false;
    }

    public boolean addCoupon(){
        Optional<CompanyEntity> company = companyRepository.findById(1L);
        if(company.isPresent()) {
            CouponEntity coupon = CouponEntity.builder()
                    .title(TestData.COUPON_TITLE)
                    .category(TestData.COUPON_CATEGORY)
                    .amount(TestData.COUPON_AMOUNT)
                    .price(TestData.COUPON_PRICE)
                    .company(company.get())
                    .description(TestData.COUPON_DESCRIPTION)
                    .startDate(TestData.COUPON_START_DATE)
                    .endDate(TestData.COUPON_EXPIRE_DATE)
                    .image(TestData.COUPON_IMAGE.getBytes())
                    .build();

            try {
                ResponseEntity<CouponEntity> response = restTemplate().postForEntity(
                        "http://localhost:8080/companies/coupons/", coupon, CouponEntity.class
                );

                CouponEntity couponRes = response.getBody();
                return couponRes != null;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        return false;
    }
    public boolean updateCoupon(){
        Optional<CompanyEntity> company = companyRepository.findById(2L);
        if(company.isPresent()) {
            CouponEntity coupon = CouponEntity.builder()
                    .id(TestData.COUPON_ID)
                    .title(TestData.COUPON_TITLE)
                    .category(TestData.COUPON_CATEGORY)
                    .amount(TestData.COUPON_AMOUNT)
                    .price(TestData.COUPON_PRICE_UPDATE)
                    .company(company.get())
                    .description(TestData.COUPON_DESCRIPTION)
                    .startDate(TestData.COUPON_START_DATE)
                    .endDate(TestData.COUPON_EXPIRE_DATE)
                    .image(TestData.COUPON_IMAGE.getBytes())
                    .build();
            try {
                restTemplate().put(
                        "http://localhost:8080/companies/coupons/" + coupon.getId(), coupon, CouponEntity.class
                );

                Optional<CouponEntity> couponFromDB = couponRepository.findById(coupon.getId());
                if (couponFromDB.isPresent() && couponFromDB.get().getPrice() == coupon.getPrice()) {
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        return false;
    }

    public boolean deleteCoupon(){
        try{
            restTemplate().delete(
                    "http://localhost/companies/coupons/" + 1
            );
            Optional<CouponEntity> coupon = couponRepository.findById(1L);
            if(coupon.isPresent()){
                return false;
            }else{
                return true;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean getCompanyCoupons(){
        try{
            ResponseEntity<CouponEntity[]> response = restTemplate().getForEntity(
                    "http://localhost:8080/companies/coupons/" + 1,CouponEntity[].class
            );
            CouponEntity[] couponEntities = response.getBody();
            if(couponEntities !=null && couponEntities.length>0){
                return true;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    public boolean getCompanyCouponsByCategory(){
        try{
            ResponseEntity<CouponEntity[]> response = restTemplate().getForEntity(
                    "http://localhost:8080/companies/coupons/2/2/",CouponEntity[].class
            );

            CouponEntity[] couponEntities = response.getBody();
            if(couponEntities !=null && couponEntities.length>0){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean getCompanyCouponsPriceLessThanMaxPrice(){
        try{
            ResponseEntity<CouponEntity[]> response = restTemplate().getForEntity(
                    "http://localhost:8080/companies/coupons/max_price/2/200/",CouponEntity[].class
            );

            CouponEntity[] couponEntities = response.getBody();
            if(couponEntities !=null && couponEntities.length>0){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean purchaseCoupon(){
        try{
            restTemplate().optionsForAllow(
                    "http://localhost/customers/purchases/1/1"
            );
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
