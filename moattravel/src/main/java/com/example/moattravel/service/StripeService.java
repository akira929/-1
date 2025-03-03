package com.example.moattravel.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.moattravel.form.ReservationRegisterForm;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class StripeService {
	@Value("${stripe.api-key}")
	private String stripeApiKey;
	
	//セッションを作成し、Stripeに必要な情報を返す
	public String createStripeSession(String houseName,
			ReservationRegisterForm reservationRegisterForm, HttpServletRequest httpServletRequest) {
		Stripe.apiKey = "sk_test_51QyRp0GWsQLen8yaraxG10vsHcLCOu06dBCieCuCYe8JqmjZrC9DExuU9U8IJ2Lt8rX9SIaOmdl07SP6TFGFRPNA00p68LAh0q";
		Stripe.apiKey =stripeApiKey;
		String requestUrl = new String(httpServletRequest.getRequestURL());
		SessionCreateParams params = SessionCreateParams.builder()
				.addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
				.addLineItem(
						SessionCreateParams.LineItem.builder()
								.setPriceData(
										SessionCreateParams.LineItem.PriceData.builder()
												.setProductData(
														SessionCreateParams.LineItem.PriceData.ProductData.builder()
																.setName(houseName)
																.build())
												.setUnitAmount((long) reservationRegisterForm.getAmount())
												.setCurrency("jpy")
												.build())
								.setQuantity(1L)
								.build())
				.setMode(SessionCreateParams.Mode.PAYMENT)
				.setSuccessUrl(
						requestUrl.replaceAll("/houses/[0-9]+/reservations/confirm", "") + "/reservations?reserved")
				.setCancelUrl(requestUrl.replace("/reservations/confirm", ""))
				.setPaymentIntentData(
						SessionCreateParams.PaymentIntentData.builder()
								.putMetadata("houseId", reservationRegisterForm.getHouseId().toString())
								.putMetadata("userId", reservationRegisterForm.getUserId().toString())
								.putMetadata("checkinDate", reservationRegisterForm.getCheckinDate())
								.putMetadata("checkoutDate", reservationRegisterForm.getCheckoutDate())
								.putMetadata("numberOfPeople", reservationRegisterForm.getNumberOfPeople().toString())
								.putMetadata("amount", reservationRegisterForm.getAmount().toString())
								.build())
				.build();
		try {
			Session session = Session.create(params);
			return session.getId();
		} catch (StripeException e) {
			e.printStackTrace();
			return "";

		}

	}

}
