package com.example.demo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService
{
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ProductClient productClient;
	@Autowired
	private CartClient cartClient;
	@Autowired
	private IdentityClient identityClient;

	public List<Orders> getOrders(int id)
	{
		return orderRepository.findByUserId(id)
				.get();
	}

	public String createOrder(int userId)
	{

//		, List<Product> products, String shippingDetails
		List<Product> products = cartClient.getCart(userId);
		// Needs to check if user exists
		String shippingDetails = identityClient.getAddress(userId);

		Orders oe = orderRepository.save(Orders.builder()
				.userId(userId)
				.products(products.stream()
						.map(p -> p.getName() + "x" + p.getQuantity() + " "
								+ String.format("%.2f", p.getPrice() * p.getQuantity()))
						.collect(Collectors.joining("<br>")))
				.shippingDetails(shippingDetails)
				.total(products.stream()
						.map(d -> d.getPrice() * d.getQuantity())
						.reduce(0.0, (a, b) -> (a + b)))
				.build());
		System.out.println(oe.getProducts());
		return "Order Successfully placed";

	}

}