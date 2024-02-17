package com.ispan.mingle.projmingle.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

	@GetMapping("/ecpayCheckout/{amount}/{id}")
	public String ecpayCheckout(@PathVariable String amount, @PathVariable Integer id,HttpServletRequest request) {
		String aioCheckOutALLForm = paymentService.genAioCheckOutALL(amount);

		// Check if this request is a callback from ECPay
		String paymentStatus = request.getParameter("PaymentStatus");
		if (paymentStatus != null) {
			System.out.println("9999999999999999999999999");
			// This request is a callback from ECPay
			handleECPayCallback(request.getParameterMap(), id);
			return "Payment callback processed successfully.";
		}
		System.out.println("33333333333333333333333333333");
		orderService.setOrderStatus(id, "已完成訂單", false);
		return aioCheckOutALLForm;
	}

	@PostMapping("/ecpayCallback")
	public void handleECPayCallback(Map<String, String[]> paramMap, Integer id) {
		// Extract necessary parameters (such as payment status)
		String[] paymentStatusArray = paramMap.get("PaymentStatus");

		// Check if paymentStatusArray is not null and has at least one value
		if (paymentStatusArray != null && paymentStatusArray.length > 0) {
			String paymentStatus = paymentStatusArray[0]; // Extract the first value
			System.out.println("7777777777777777777777777777777777");
			System.out.println(paymentStatus);
			if ("Paid".equals(paymentStatus)) {
				// Payment is successful
				orderService.setOrderStatus(id, "已完成訂單", false);
			} else {
				// Payment failed or in another state
				
			}
		} else {
			// Payment status not found or empty
			System.out.println("6666666666666666666666666666666");
		}
	}

}
