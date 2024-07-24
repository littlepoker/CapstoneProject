package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wishlist
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(unique = true)
	private int userId;
	private String data;
	
	public List<Integer> getData()
	{
		List<Integer> result = new ArrayList<Integer>();
		if(data.equals(""))
		{
			return result;
		}
		for (String s : data.split(","))
		{
			result.add(Integer.parseInt(s));
		}
		return result;
	}
	
	public void setData(List<Integer> newData)
	{
		if(newData.isEmpty())
		{
			data = "";
		}
		data = newData.stream()
				.map(String::valueOf)
				.collect(Collectors.joining(","));
	}
}
