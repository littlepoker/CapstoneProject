package com.example.demo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient (name = "identity-service", url = "localhost:9898/auth")
public interface SecurityClient
{
	@PostMapping("/validate")
	public String validate(@RequestParam("token") String jwtToken);
}
