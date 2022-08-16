package com.david.coupons.services;

import com.david.coupons.entities.CustomerEntity;
import com.david.coupons.enums.Category;
import com.david.coupons.entities.CouponEntity;
import com.david.coupons.exceptions.ApplicationException;
import com.david.coupons.repositories.CouponRepository;
import com.david.coupons.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService{
    //Dependencies
    private final CouponRepository couponRepository;
    private final CustomerRepository customerRepository;

    public void purchaseCoupon(final long customerId,final long couponId) throws ApplicationException {
        Optional<CouponEntity> coupon = couponRepository.findById(couponId);
        if(coupon.isPresent()){
            Optional<CustomerEntity> customer = customerRepository.findById(customerId);
            if(customer.isPresent()){
                Set<CustomerEntity> customerEntitySet = coupon.get().getCustomers();
                if(!customerEntitySet.contains(customer.get())){
                    if(coupon.get().getAmount() > 0){
                        Date now = new Date(System.currentTimeMillis());
                        if(coupon.get().getEndDate().after(now)){
                            customer.get().getCoupons().add(coupon.get());
                            customerRepository.save(customer.get());
                            coupon.get().setAmount(coupon.get().getAmount()-1);
                        }else{
                            throw new ApplicationException("this coupon has expired");
                        }
                    }else{
                        throw new ApplicationException("The coupon is out of stock");
                    }
                }else{
                    throw new ApplicationException("You have already purchased this coupon");
                }
            }else{
                throw new ApplicationException("Customer not exists");
            }
        }else{
            throw new ApplicationException("Coupon not exists");
        }
    }

    public Set<CouponEntity> getCustomerCoupons(final long customerId) throws ApplicationException {
        Optional<CustomerEntity> customer = customerRepository.findById(customerId);
        if(customer.isPresent()){
            return customer.get().getCoupons();
        }
            throw new ApplicationException("Failed to retrieve customer with id " + customerId + " from the database");
    }

    public Set<CouponEntity> getCustomerCouponsByCategory(final long customerId,final Category category) throws ApplicationException {
        Optional<CustomerEntity> customer = customerRepository.findById(customerId);
        Set<CouponEntity> couponsByCategory = new HashSet<>();
        if(customer.isPresent()){
            Set<CouponEntity> couponEntities = customer.get().getCoupons();

            for (CouponEntity coupon: couponEntities){
                if(coupon.getCategory().equals(category)){
                    couponsByCategory.add(coupon);
                }
            }
            return couponsByCategory;
        }
        throw new ApplicationException("Failed to retrieve customer with id " + customerId);
    }

    public Set<CouponEntity> getCustomerCouponsPriceLessThan(final long customerId,final double maxPrice) throws ApplicationException {
        Optional<CustomerEntity> customer = customerRepository.findById(customerId);
        Set<CouponEntity> lowestCouponEntities = new HashSet<>();
        if(customer.isPresent()){
            Set<CouponEntity> couponEntities = customer.get().getCoupons();

            for (CouponEntity coupon: couponEntities){
                if(coupon.getPrice() <= maxPrice){
                    lowestCouponEntities.add(coupon);
                }
            }
            return lowestCouponEntities;
        }
        throw new ApplicationException("Failed to retrieve customer with id " + customerId);

    }

    public CustomerEntity getOneCustomer(final long customerId) throws ApplicationException {
        Optional<CustomerEntity> customer = customerRepository.findById(customerId);
        if(customer.isPresent()){
            return customer.get();
        }
        throw new ApplicationException("Failed to retrieve customer with id " + customerId);
    }

    public CustomerEntity getByEmail(String email){
        return customerRepository.findByEmail(email);
    }
    public List<CouponEntity> getAllCoupons(){
        return couponRepository.findAll();
    }
}
