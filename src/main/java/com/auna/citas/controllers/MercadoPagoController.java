package com.auna.citas.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;

@RestController
@RequestMapping("/api/v1")
public class MercadoPagoController {

	@GetMapping("/mercado")
	public String mercado() throws MPException, MPApiException {
		
		MercadoPagoConfig.setAccessToken("APP_USR-5484899728014966-100700-1b339c266e59f8b991963fdb3ff917ce-2908928433");
		
		PreferenceBackUrlsRequest backUrls =
				   PreferenceBackUrlsRequest.builder()
				       .success("https://acortar.link/UpfqyY") //http://localhost:5173/pago-exitoso
				       .pending("http://localhost:5173/pago-pendiente")
				       .failure("http://localhost:5173/pago-fallido")
				       .build();	
		
		PreferenceItemRequest itemRequest =
			       PreferenceItemRequest.builder()
			           .id("P-01")
			           .title("Consulta")
			           .description("Atencion de consulta")
			           .categoryId("C-01")
			           .quantity(1)
			           .currencyId("PEN")
			           .unitPrice(new BigDecimal("50"))
			           .build();
		
		List<PreferenceItemRequest> items = new ArrayList<>();
		
		items.add(itemRequest);
			   
		PreferenceRequest preferenceRequest = PreferenceRequest.builder()
					.items(items)
					.backUrls(backUrls)
					.build();
			
		PreferenceClient client = new PreferenceClient();
			
		Preference preference = client.create(preferenceRequest);
		
		return preference.getInitPoint();
	}
	
}
