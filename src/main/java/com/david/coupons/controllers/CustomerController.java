package com.david.coupons.controllers;

import com.david.coupons.entities.CompanyEntity;
import com.david.coupons.entities.CouponEntity;
import com.david.coupons.entities.CustomerEntity;
import com.david.coupons.enums.Category;
import com.david.coupons.exceptions.ApplicationException;
import com.david.coupons.services.CompanyService;
import com.david.coupons.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    private final CompanyService companyService;

    @GetMapping("/purchase")
    public void purchaseCoupon(@RequestParam final long customerId,@RequestParam final long couponId) throws ApplicationException {
        customerService.purchaseCoupon(customerId,couponId);
    }

    @GetMapping("/purchases/{customerId}")
    public Set<CouponEntity> getCustomerPurchases(@PathVariable final  long customerId) throws ApplicationException {
        return customerService.getCustomerCoupons(customerId);
    }

    @GetMapping("/purchases/category/{customerId}")
    public Set<CouponEntity> getCustomerPurchases(@PathVariable final long customerId,@RequestParam String category) throws ApplicationException {
        return customerService.getCustomerCouponsByCategory(customerId, Category.valueOf(category));
    }

    @GetMapping("/purchases/max_price/{customerId}")
    public Set<CouponEntity> getCustomerPurchasesByMaxPrice(@PathVariable final long customerId,@RequestParam String maxPrice) throws ApplicationException {
        return customerService.getCustomerCouponsPriceLessThan(customerId,Double.parseDouble(maxPrice));
    }
    @GetMapping("/coupons")
    public List<CouponEntity> getAllCoupons(){
        return customerService.getAllCoupons();
    }

    @GetMapping("{customerId}")
    public CustomerEntity getOneCustomer(@PathVariable final long customerId) throws ApplicationException {
        return customerService.getOneCustomer(customerId);
    }

    @GetMapping("/coupon/{couponId}")
    public CouponEntity getOneCoupon(@PathVariable long couponId) throws ApplicationException {
        return companyService.getOneCoupon(couponId);
    }
}
