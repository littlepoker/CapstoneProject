package com.example.demo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "cart-service", url = "localhost:8084/cart")
public interface CartClient
{
	@PostMapping("/init/{userId}")
	public void initCart(@PathVariable("userId") int userId);

	@DeleteMapping("/{userId}")
	public void deleteCart(@PathVariable("userId") int userId);

}
