package com.oneSoft.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oneSoft.entities.Coupon;

public interface Icoupon extends JpaRepository<Coupon, Long> {

}
