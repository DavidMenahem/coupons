package com.david.coupons.repositories;

import com.david.coupons.entities.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity,Long> {
    CompanyEntity findByEmail(final String email);
}
