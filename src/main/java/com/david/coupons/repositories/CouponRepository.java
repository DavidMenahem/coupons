package com.david.coupons.repositories;

import com.david.coupons.entities.CouponEntity;
import com.david.coupons.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Set;

@Repository
public interface CouponRepository extends JpaRepository<CouponEntity,Long> {
    List<CouponEntity> getByCompanyId(final long companyID);

    List<CouponEntity> findByCompanyId(final long companyId);

    List<CouponEntity> findByCompanyIdAndCategory(final long companyID, final Category category);

    List<CouponEntity> findByCompanyIdAndPriceLessThanEqual(final long companyId, final double maxPrice);

    @Query(value = "SELECT * FROM coupons WHERE end_date < ?1", nativeQuery = true)
    Set<CouponEntity> getExpiredCoupons(Date now);

    void deleteByCompanyId(long companyId);
}
