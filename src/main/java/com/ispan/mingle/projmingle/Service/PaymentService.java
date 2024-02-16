package com.ispan.mingle.projmingle.Service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PaymentService {
	@Value("${front.end.host}")
    private String front;

	public String genAioCheckOutALL(String amount){
		String uuId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);
		AioCheckOutALL obj = new AioCheckOutALL();
		AllInOne all = new AllInOne("");
		obj.setMerchantTradeNo(uuId);
		obj.setMerchantTradeDate("2017/01/01 08:05:23");
		obj.setTotalAmount(amount);
		obj.setTradeDesc("test Description");
		obj.setItemName("TestItem");
		obj.setReturnURL(front);
		obj.setClientBackURL(front);
		obj.setNeedExtraPaidInfo("Y");
		String form = all.aioCheckOut(obj, null);
		return form;
	}
}
