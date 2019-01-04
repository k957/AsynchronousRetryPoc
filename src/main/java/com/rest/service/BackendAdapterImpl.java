package com.rest.service;

import java.time.LocalTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.rest.exception.RemoteServiceNotAvailableException;
import com.rest.pojo.User;

@Service
public class BackendAdapterImpl implements BackendAdapter{
	
	@Autowired
	RestTemplate restTemplate;

	@Override
	public String getBackendResponse(boolean simulateretry, boolean simulateretryfallback) {
		
		if(simulateretry) {
			System.out.println("Simulateretry is true, so try to simulate exception scenerio.");
			
			if(simulateretryfallback) {
				throw new RemoteServiceNotAvailableException(
                        "Don't worry!! Just Simulated for Spring-retry..Must fallback as all retry will get exception!!!");
			}
			int random = new Random().nextInt(4);
			
			System.out.println("Random Number: "+random);
			if(random % 2 == 0) {
				throw new RemoteServiceNotAvailableException("just simulated for spring retry");
			}
		}
		return "Hello from Remote Backend!!";
	}

	@Override
	public String getBackendResponseFallback(RuntimeException e) {
		return "All retries completed, so Fallback method called !!!";
	}

	@Override
	public ResponseEntity<?> getBackendResponse() {
		System.out.println("access made at: "+LocalTime.now());
		ResponseEntity<?> quoteResponse = null;
		quoteResponse = restTemplate.exchange("http://localhost:8096/v2/merchant/merchantId/6", HttpMethod.GET,null,User.class);
		System.out.println(quoteResponse);
		if(quoteResponse == null) {
			
			throw new RemoteServiceNotAvailableException("Server error, retrying in 10 mins");
		}
		return new ResponseEntity<>(quoteResponse.getBody(),HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getBackendResponse(RuntimeException e) {
		
		throw new RemoteServiceNotAvailableException("Server error, All retries completed");
	}

}
