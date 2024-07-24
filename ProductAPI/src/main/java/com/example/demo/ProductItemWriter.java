package com.example.demo;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductItemWriter implements ItemWriter<Product>{
	
	private final ProductRepository productRepository;
	
	@Override
	public void write(Chunk<? extends Product> chunkList) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Writer Thread " + Thread.currentThread().getName());
		productRepository.saveAll(chunkList);
	}

}
