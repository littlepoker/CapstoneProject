package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/cart")
public class CartController {
	@Autowired
	private CartService cartService;
	
	@GetMapping("/{userId}")
	public List<Product> getCart(@PathVariable int userId)
	{
		return cartService.getCart(userId);
	}
	
	@PostMapping ("/{userId}/add")
	public List<Product> addItem(@PathVariable int userId, @RequestParam int productId)
	{
		cartService.addItem(userId, productId);
		return getCart(userId);
	}
	
	@DeleteMapping("/{userId}/remove/{productId}")
	public List<Product> removeItem(@PathVariable int userId, @PathVariable int productId)
	{
		cartService.removeItem(userId, productId);
		return getCart(userId);
	}
	
	@PutMapping ("/{userId}/update")
	public Cart updateCart(@PathVariable int userId, @RequestBody List<CartItem> cartItems)
	{
		return cartService.updateCart(userId, cartItems);
	}
	
	@PostMapping("/init/{userId}")
	public void initCart(@PathVariable int userId)
	{
		cartService.init(userId);
	}
	@DeleteMapping("/{userId}")
	public void deleteCart(@PathVariable int userId)
	{
		cartService.delete(userId);
	}
	@DeleteMapping("/{userId}/clear")
	public void clearCart(@PathVariable int userId)
	{
		cartService.clear(userId);
	}
}
