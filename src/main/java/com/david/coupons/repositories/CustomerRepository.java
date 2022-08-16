package com.david.coupons.repositories;

import com.david.coupons.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity,Long> {
    CustomerEntity findByEmail(final String email);
}
