package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product
{
	private int id;	
	private String name;
	private String description;
	private double price;
	private String category;
	private int quantity;
}
