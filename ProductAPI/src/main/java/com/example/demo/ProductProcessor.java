package com.example.demo;

import org.springframework.batch.item.ItemProcessor;

public class ProductProcessor implements ItemProcessor<Product, Product>{

	@Override
	public Product process(Product item) throws Exception {
		// TODO Auto-generated method stub
		return item;
	}

}
