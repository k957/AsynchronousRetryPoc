package com.rest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rest.service.BackendAdapter;

@RestController
public class RetryController {

	@Autowired
    BackendAdapter backendAdapter;
	
	
 
    @GetMapping("/retry")
    public String validateSpringRetryCapability(@RequestParam(required = false) boolean simulateretry,
                                @RequestParam(required = false) boolean simulateretryfallback)
    {
        System.out.println("===============================");
        System.out.println("Inside RestController method..");
 
        return backendAdapter.getBackendResponse(simulateretry, simulateretryfallback);
        
    }
    
    @GetMapping("/retry2")
    public ResponseEntity<?> validateSpringRetryCapability()
    {
        System.out.println("===============================");
        System.out.println("Inside RestController method..");
 
        return backendAdapter.getBackendResponse();
        
    }

}
