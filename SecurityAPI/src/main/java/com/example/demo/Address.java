package com.example.demo;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Builder
public class Address
{
	private int houseNumber;
	 private String street;
	 private String city; 
	 private String zipcode;
	 
	 public String toString()
	 {
		 return String.valueOf(houseNumber) + " " + street + " " + city + ", " + zipcode;
	 }
}