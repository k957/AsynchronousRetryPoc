package com.rest.service;

import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

import com.rest.exception.RemoteServiceNotAvailableException;

public interface BackendAdapter {

	@Retryable(value = {
			RemoteServiceNotAvailableException.class
	}, maxAttempts = 3, backoff = @Backoff(delay = 1000))
	public String getBackendResponse(boolean simulateretry, boolean simulateretryfallback);
	
	@Retryable(value = {Exception.class
	}, maxAttempts = 3, backoff = @Backoff(delay = 600000))
	public ResponseEntity<?> getBackendResponse();
	
	@Recover
	public String getBackendResponseFallback(RuntimeException e);
	
	@Recover
	public ResponseEntity<?> getBackendResponse(RuntimeException e);
}
