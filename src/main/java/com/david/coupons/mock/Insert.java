package com.david.coupons.mock;

import com.david.coupons.constants.TestData;
import com.david.coupons.entities.CompanyEntity;
import com.david.coupons.entities.CouponEntity;
import com.david.coupons.entities.CustomerEntity;
import com.david.coupons.enums.Category;
import com.david.coupons.exceptions.ApplicationException;
import com.david.coupons.repositories.CompanyRepository;
import com.david.coupons.repositories.CustomerRepository;
import com.david.coupons.services.AdminService;
import com.david.coupons.services.CompanyService;
import com.david.coupons.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.HashSet;
import java.util.Random;


@Component
@RequiredArgsConstructor
public class Insert {
    private final CompanyService companyService;
    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;
    public void run() throws ApplicationException {
        Random rand = new Random();
        for(int i = 1;i<10;i++) {
            CompanyEntity companyEntity = CompanyEntity.builder()
                    .name(TestData.COMPANY_NAME + " " + i)
                    .email(i + TestData.COMPANY_EMAIL)
                    .password(TestData.COMPANY_PASSWORD.hashCode())
                    .build();
            Date startDate = new Date(System.currentTimeMillis());
            Date expireDate = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 10);

            CompanyEntity savedCompanyEntity = companyRepository.save(companyEntity);
            if(i==2) {
                startDate = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 10);
                expireDate = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 5);
            }
            CouponEntity couponEntity = CouponEntity.builder()
                    .amount((i) * 10)
                    .category(Category.values()[rand.nextInt(4)])
                    .company(savedCompanyEntity)
                    .description("description " + i)
                    .endDate(expireDate)
                    .startDate(startDate)
                    .price(rand.nextInt(200) +1)
                    .title("Book" + i)
                    .build();

            CouponEntity savedCouponEntity = companyService.addCoupon(couponEntity);

            CustomerEntity customerEntity = CustomerEntity.builder()
                    .firstName(TestData.CUSTOMER_FIRST_NAME + " " + i)
                    .lastName(TestData.CUSTOMER_LAST_NAME + " " + i)
                    .email(i + TestData.CUSTOMER_EMAIL)
                    .password(TestData.CUSTOMER_PASSWORD.hashCode())
                    .build();
            CustomerEntity savedCustomerEntity = customerRepository.save(customerEntity);

            savedCustomerEntity.setCoupons(new HashSet<>());
            savedCouponEntity.setCustomers(new HashSet<>());
            savedCustomerEntity.addCoupon(savedCouponEntity);
            customerRepository.save(savedCustomerEntity);
        }
    }
}
