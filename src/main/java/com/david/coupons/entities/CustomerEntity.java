package com.david.coupons.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "customers")
public class CustomerEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="first_name", nullable = false)
    private java.lang.String firstName;
    @Column(name="last_name", nullable = false)
    private java.lang.String lastName;

    @Column(name="email", nullable = false, unique = true)
    private java.lang.String email;
    @Column(name="password", nullable = false)
    private int password;
    @JsonIgnore
    @ManyToMany
    @JoinTable(name="coupon_vs_customer",
            joinColumns = @JoinColumn(name="customer_id"),
            inverseJoinColumns = @JoinColumn(name = "coupon_id"))
    private Set<CouponEntity> coupons = new HashSet<>();

    public void addCoupon(CouponEntity coupon){
        this.coupons.add(coupon);
       coupon.getCustomers().add(this);
    }

    public void removeCoupon(CouponEntity coupon){
        this.coupons.remove(coupon);
        coupon.getCustomers().remove(this);
    }
    @Override
    public java.lang.String toString() {
        return "CustomerEntity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password=" + password +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public java.lang.String getFirstName() {
        return firstName;
    }

    public void setFirstName(java.lang.String firstName) {
        this.firstName = firstName;
    }

    public java.lang.String getLastName() {
        return lastName;
    }

    public void setLastName(java.lang.String lastName) {
        this.lastName = lastName;
    }

    public java.lang.String getEmail() {
        return email;
    }

    public void setEmail(java.lang.String email) {
        this.email = email;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public Set<CouponEntity> getCoupons() {
        return coupons;
    }

    public void setCoupons(Set<CouponEntity> coupons) {
        this.coupons = coupons;
    }
}
