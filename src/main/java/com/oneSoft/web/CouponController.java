package com.oneSoft.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oneSoft.dao.Icoupon;



@Controller
public class CouponController {
	@Autowired
	private Icoupon icoupon;
	
	@RequestMapping(value="/coupons")
	public String getAllcoupon(Model model) {
		model.addAttribute("coupons", icoupon.findAll());
		return "EspaceAdmin/coupons";
	}

}
