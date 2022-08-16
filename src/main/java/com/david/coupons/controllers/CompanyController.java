package com.david.coupons.controllers;

import com.david.coupons.entities.CompanyEntity;
import com.david.coupons.entities.CouponEntity;
import com.david.coupons.enums.Category;
import com.david.coupons.exceptions.ApplicationException;
import com.david.coupons.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;


    @PostMapping(value ="/coupons",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public CouponEntity addCoupon(@RequestParam final MultipartFile imageFile,
                                  @RequestParam final long companyId,
                                  @RequestParam final String title,
                                  @RequestParam final double price,
                                  @RequestParam final String startDate,
                                  @RequestParam final String endDate,
                                  @RequestParam final int amount,
                                  @RequestParam final String description,
                                  @RequestParam final String category
                                  ) throws ApplicationException, IOException {
        CompanyEntity companyEntity = companyService.getOneCompany(companyId);
        CouponEntity coupon = CouponEntity.builder()
                .image(imageFile.getBytes())
                .company(companyEntity)
                .title(title)
                .price(price)
                .startDate(Date.valueOf(startDate))
                .endDate(Date.valueOf(endDate))
                .amount(amount)
                .description(description)
                .category(Category.valueOf(category))
                .build();
        return companyService.addCoupon(coupon);
    }

    @PutMapping(value = "/coupons/{couponId}",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public CouponEntity updateCoupon(@RequestParam final MultipartFile imageFile,
                                     @RequestParam final long id,
                                     @RequestParam final long companyId,
                                     @RequestParam final String title,
                                     @RequestParam final double price,
                                     @RequestParam final Date startDate,
                                     @RequestParam final Date endDate,
                                     @RequestParam final int amount,
                                     @RequestParam final String description,
                                     @RequestParam final String category
    ) throws ApplicationException, IOException {
        CompanyEntity companyEntity = companyService.getOneCompany(companyId);
        CouponEntity coupon = CouponEntity.builder()
                .id(id)
                .image(imageFile.getBytes())
                .company(companyEntity)
                .title(title)
                .price(price)
                .startDate(startDate)
                .endDate(endDate)
                .amount(amount)
                .description(description)
                .category(Category.valueOf(category))
                .build();
        return companyService.updateCoupon(coupon);
    }

    @DeleteMapping("/coupons/{couponId}")
    public void deleteCoupon(@PathVariable final long couponId) {
        companyService.deleteCoupon(couponId);
    }

    @GetMapping("/coupons/{companyId}")
    public List<CouponEntity> getAllCompanyCoupons(@PathVariable final long companyId){
        return companyService.getCompanyCoupons(companyId);
    }

    @GetMapping("/coupons/category/{companyId}")
    public List<CouponEntity> getAllCompanyCouponsByCategory(@PathVariable final long companyId,@RequestParam final String category){
        return companyService.getCompanyCouponsByCategory(companyId, Category.valueOf(category));
    }

    @GetMapping("/coupons/max_price/{companyId}")
    public List<CouponEntity> getAllCompanyCouponsByMaxPrice(@PathVariable final long companyId, @RequestParam final String maxPrice){
        return companyService.getCompanyCouponsPriceLessThan(companyId,Double.parseDouble(maxPrice));
    }

    @GetMapping("/{companyId}")
    public CompanyEntity getOneCompany(@PathVariable final long companyId) throws ApplicationException {
        return companyService.getOneCompany(companyId);
    }

    @GetMapping("/coupon/{couponId}")
    public CouponEntity getOneCoupon(@PathVariable long couponId) throws ApplicationException {
        return companyService.getOneCoupon(couponId);
    }
}
