package com.david.coupons.controllers;

import com.david.coupons.entities.CompanyEntity;
import com.david.coupons.entities.CouponEntity;
import com.david.coupons.entities.CustomerEntity;
import com.david.coupons.enums.Category;
import com.david.coupons.exceptions.ApplicationException;
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

    @PostMapping("/purchase/{customerId}/{couponId}")
    public void purchaseCoupon(@PathVariable final long customerId,@PathVariable final long couponId) throws ApplicationException {
        customerService.purchaseCoupon(customerId,couponId);
    }

    @GetMapping("/purchases/{customerId}")
    public Set<CouponEntity> getCustomerPurchases(@PathVariable long customerId) {
        return customerService.getCustomerCoupons(customerId);
    }

    @GetMapping("/purchases/category/{customerId}")
    public Set<CouponEntity> getCustomerPurchases(@PathVariable long customerId,@RequestParam String category) {
        return customerService.getCustomerCouponsByCategory(customerId, Category.valueOf(category));
    }

    @GetMapping("/purchases/max_price/{customerId}")
    public Set<CouponEntity> getCustomerPurchasesByMaxPrice(@PathVariable long customerId,@RequestParam String maxPrice) {
        return customerService.getCustomerCouponsPriceLessThan(customerId,Double.parseDouble(maxPrice));
    }
    @GetMapping("/coupons")
    public List<CouponEntity> getAllCoupons(){
        return customerService.getAllCoupons();
    }

    @GetMapping("{customerId}")
    public CustomerEntity getOneCustomer(@PathVariable long customerId){
        return customerService.getOneCustomer(customerId);
    }
}
