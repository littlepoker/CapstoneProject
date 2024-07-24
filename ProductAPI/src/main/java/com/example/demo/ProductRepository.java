package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Integer>{
	List<Product> findAllByCategory(String category);

	@Query("SELECT DISTINCT p.category FROM Product p")
	List<String> findCategories();
}
