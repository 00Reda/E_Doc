package com.CTi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CTi.entities.Coupon;

public interface Icoupons extends JpaRepository<Coupon, Long> {
	
	public Coupon findByCoupon(String coupon); 

}
