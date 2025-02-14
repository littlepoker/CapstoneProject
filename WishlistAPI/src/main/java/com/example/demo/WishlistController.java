package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wishlist")
public class WishlistController
{
	@Autowired
	private WishlistService wishlistService;

	@GetMapping("/{userId}")
	public List<Product> getList(@PathVariable int userId)
	{
		return wishlistService.getProductList(userId);
	}

	@PostMapping("/{userId}")
	public List<Product> addItem(@PathVariable int userId, @RequestParam int productId)
	{
		return wishlistService.addItem(userId, productId);
	}

	@DeleteMapping("/{userId}/remove/{productId}")
	public List<Product> deleteItem(@PathVariable int userId, @PathVariable int productId)
	{
		return wishlistService.removeItem(userId, productId);
	}

	@PostMapping("/init/{userId}")
	public void initList(@PathVariable int userId)
	{
		wishlistService.initList(userId);
	}

	@DeleteMapping("/{userId}")
	public void deleteList(@PathVariable int userId)
	{
		wishlistService.deleteList(userId);
	}

}