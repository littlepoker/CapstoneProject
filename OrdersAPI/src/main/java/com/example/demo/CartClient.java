package com.example.demo;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cart-service", url = "localhost:8084/cart")
public interface CartClient
{
	@GetMapping("/{userId}")
	public List<Product> getCart(@PathVariable("userId") int userId);
}