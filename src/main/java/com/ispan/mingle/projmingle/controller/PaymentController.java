package com.ispan.mingle.projmingle.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.mingle.projmingle.Service.OrderService;
import com.ispan.mingle.projmingle.Service.PaymentService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
public class PaymentController {
	@Autowired
	PaymentService paymentService;
	@Autowired
	OrderService orderService;

	@GetMapping("/ecpayCheckout/{amount}")
	public String ecpayCheckout(@PathVariable String amount, HttpServletRequest request) {
		String aioCheckOutALLForm = paymentService.genAioCheckOutALL(amount);

		// Check if this request is a callback from ECPay
		if (request.getParameter("PaymentStatus") != null) {
			// This request is a callback from ECPay
			handleECPayCallback(request.getParameterMap());
			return "Payment callback processed successfully.";
		}

		return aioCheckOutALLForm;
	}

	@PostMapping("/ecpayCallback")
	public void handleECPayCallback(Map<String, String[]> paramMap) {
		// Extract necessary parameters (such as payment status)
		String[] paymentStatusArray = paramMap.get("PaymentStatus");

		// Check if paymentStatusArray is not null and has at least one value
		if (paymentStatusArray != null && paymentStatusArray.length > 0) {
			String paymentStatus = paymentStatusArray[0]; // Extract the first value
			if ("Paid".equals(paymentStatus)) {
				// Payment is successful
				orderService.setOrderStatus(null, "訂單已完成", false);
			} else {
				// Payment failed or in another state
				
			}
		} else {
			// Payment status not found or empty
			
		}
	}

}
