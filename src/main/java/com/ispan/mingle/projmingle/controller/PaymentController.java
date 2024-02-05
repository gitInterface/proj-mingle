package com.ispan.mingle.projmingle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.mingle.projmingle.Service.PaymentService;

@RestController
@CrossOrigin
public class PaymentController {
    @Autowired
	PaymentService paymentService;

	@GetMapping("/ecpayCheckout/{amount}")
	public String ecpayCheckout(@PathVariable String amount) {
		String aioCheckOutALLForm = paymentService.genAioCheckOutALL(amount);
		
		return aioCheckOutALLForm;
	}
}