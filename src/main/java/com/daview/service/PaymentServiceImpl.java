package com.daview.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.daview.dto.PaymentDTO;
import com.daview.dto.PaymentWithReservationsDTO;
import com.daview.mapper.PaymentMapper;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
	
	private final PaymentMapper paymentMapper;
	
	@Override
	public int insertPayment(PaymentDTO payment) {
		return paymentMapper.insertPayment(payment);
	}
	
	@Override
	public PaymentDTO selectPaymentById(String pymId) {
		return paymentMapper.selectPaymentById(pymId);
	}
	
	@Override
	public List<PaymentWithReservationsDTO> selectPaymentWithReservationsByMemberId(Long memberId){
		return paymentMapper.selectPaymentWithReservationsByMemberId(memberId);
	}
	
	@Override
	public List<String> getProdNmList(Long memberId){
		return paymentMapper.getProdNmList(memberId);
	}

	@Value("${iamport.api.key}")
	private String apiKey;

	@Value("${iamport.api.secret}")
	private String apiSecret;

	@Override
	public boolean cancelPayment(String impUid, String refundReason) throws Exception {
	    IamportClient iamportClient = new IamportClient(apiKey, apiSecret);

	    CancelData cancelData = new CancelData(impUid, true);
	    IamportResponse<Payment> response = iamportClient.cancelPaymentByImpUid(cancelData);

	    if (response != null && response.getResponse() != null &&
	        "cancelled".equals(response.getResponse().getStatus())) {

	        int updated = paymentMapper.updatePaymentStatusByImpUid(impUid, 4, refundReason);
	        return updated > 0;
	    }

	    return false;
	}
	
	@Override
    public List<PaymentWithReservationsDTO> getRefundedPaymentsByMemberId(Long memberId) {
        return paymentMapper.selectRefundedPaymentsByMemberId(memberId);
    }
}
