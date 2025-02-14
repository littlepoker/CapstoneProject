package com.example.demo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "wishlist-service", url = "localhost:8082/wishlist")
public interface WishlistClient
{
	@PostMapping("/init/{userId}")
	public void initList(@PathVariable("userId") int userId);

	@DeleteMapping("/{userId}")
	public void deleteList(@PathVariable("userId") int userId);

}
