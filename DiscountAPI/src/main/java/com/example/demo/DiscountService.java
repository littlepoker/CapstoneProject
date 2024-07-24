package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscountService
{
	@Autowired
	private DiscountRepository discountRepository;
	
	public int findDiscount(String code)
	{
		return discountRepository.findByCode(code).get().getSaving();
	}

	public Discount addDiscount(Discount discount)
	{
		return discountRepository.save(discount);
	}

	public void delete(int id)
	{
		discountRepository.deleteById(id);
	}
	
	
}
