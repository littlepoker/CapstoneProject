package com.example.demo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "identity-service", url = "localhost:9898/auth")
public interface IdentityClient
{
	@PostMapping("/address/{userId}")
	public String getAddress(@PathVariable ("userId") int userId);
}

