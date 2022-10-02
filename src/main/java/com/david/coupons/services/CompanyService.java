package com.david.coupons.services;
import com.david.coupons.entities.CompanyEntity;
import com.david.coupons.entities.CouponEntity;
import com.david.coupons.entities.CustomerEntity;
import com.david.coupons.enums.Category;
import com.david.coupons.exceptions.ApplicationException;
import com.david.coupons.repositories.CompanyRepository;
import com.david.coupons.repositories.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyService {
    //Dependencies
    private final CompanyRepository companyRepository;
    private final CouponRepository couponRepository;

    //Methods

    public CouponEntity addCoupon(final CouponEntity couponEntity) throws ApplicationException {
        List<CouponEntity> couponEntities = couponRepository.getByCompanyId(couponEntity.getCompany().getId());
        boolean titleExists = false;
        for(CouponEntity coupon: couponEntities){
            if(coupon.getTitle().equals(couponEntity.getTitle())){
                titleExists = true;
                break;
            }
        }
        if(titleExists){
            throw new ApplicationException("Title " + couponEntity.getTitle() + " already exists in this company");
        }
        return couponRepository.save(couponEntity);
    }

    public CouponEntity updateCoupon(final CouponEntity couponEntity) throws ApplicationException {
        List<CouponEntity> couponEntities = couponRepository.getByCompanyId(couponEntity.getCompany().getId());
        boolean titleExists = false;
        for(CouponEntity coupon: couponEntities){
            if(coupon.getId() == couponEntity.getId()){
                continue;
            }
            if(coupon.getTitle().equals(couponEntity.getTitle())){
                titleExists = true;
                break;
            }
        }
        if(titleExists){
            throw new ApplicationException("Title " + couponEntity.getTitle() + " already exists in this company");
        }
        return couponRepository.save(couponEntity);
    }

    public void deleteCoupon(final long couponId) {
        Optional<CouponEntity> coupon = couponRepository.findById(couponId);
        if (coupon.isPresent()) {
                Set<CustomerEntity> customers = coupon.get().getCustomers();
                for (CustomerEntity customer : customers) {
                    coupon.get().removeCustomer(customer);

            }
            couponRepository.deleteById(couponId);
        }
    }
    public List<CouponEntity> getCompanyCoupons(final long companyId){
        return couponRepository.findByCompanyId(companyId);
    }

    public List<CouponEntity> getCompanyCouponsByCategory(final long companyId,final Category category){
        return couponRepository.findByCompanyIdAndCategory(companyId,category);
    }
    public List<CouponEntity> getCompanyCouponsPriceLessThan(final long companyId,final double maxPrice){
        return couponRepository.findByCompanyIdAndPriceLessThanEqual(companyId,maxPrice);
    }

    public CompanyEntity getOneCompany(final long companyId) throws ApplicationException {
        Optional<CompanyEntity> company = companyRepository.findById(companyId);
        if(company.isPresent()){
            return company.get();
        }
            throw new ApplicationException("Failed to retrieve company from the database with id " + companyId);

    }

    public CompanyEntity getByEmail(String email){
        return companyRepository.findByEmail(email);
    }

    public CouponEntity getOneCoupon(long couponId) throws ApplicationException {
        Optional<CouponEntity> coupon = couponRepository.findById(couponId);
        if(coupon.isPresent()){
            return coupon.get();
        }
            throw  new ApplicationException("Failed to retrieve coupon with id " + couponId +  "from the database");

    }

    public Set<CouponEntity> getExpiredCoupons(){
        Date now = new Date(System.currentTimeMillis());
        return couponRepository.getExpiredCoupons(now);
    }
}
