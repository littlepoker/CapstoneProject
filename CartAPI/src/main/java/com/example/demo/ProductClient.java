package com.example.demo;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "localhost:8081/product")
public interface ProductClient
{
	@GetMapping("/products/{id}")
	public Product oneProduct(@PathVariable("id") int id);
}