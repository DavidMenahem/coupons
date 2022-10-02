package com.david.coupons.services;

import com.david.coupons.entities.CustomerEntity;
import com.david.coupons.entities.CouponEntity;
import com.david.coupons.repositories.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Set;


@Component
@Transactional
@RequiredArgsConstructor
public class DailyJobService {

    private final CompanyService companyService;

    public void check(){
        Set<CouponEntity> coupons = companyService.getExpiredCoupons();
        for (CouponEntity coupon: coupons){
                Set<CustomerEntity> customers = coupon.getCustomers();
                for (CustomerEntity customer : customers) {
                    coupon.removeCustomer(customer);
                }
                companyService.deleteCoupon(coupon.getId());
                System.out.println("deleted coupon " + coupon.getId());
            }
        }
    public void stop(){
        Thread.interrupted();
    }
}
