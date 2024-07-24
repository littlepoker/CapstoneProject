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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/product")
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@GetMapping ("/products")
	public List<Product> allProducts()
	{
		return productService.fetchAllProducts();
	}
	
	@PostMapping ("/admin/products")
	public Product addProduct(@RequestBody Product productEntity)
	{
		return productService.addProduct(productEntity);
	}
	
	@GetMapping("/products/{id}")
	public Product oneProduct(@PathVariable int id)
	{
		return productService.getProduct(id);
	}
	
	@PutMapping ("/admin/products/{id}")
	public Product updateProduct(@RequestBody Product product,@PathVariable int id)
	{
		return productService.updateProduct(product, id);
	}
	
	@PostMapping ("admin/products/bulk")
	public void bulkUpload() {
		productService.bulkUpload();
	}
	
	@DeleteMapping ("/admin/products/{id}")
	public void deleteProduct(@PathVariable int id)
	{
		productService.deleteProduct(id);
	}
	@GetMapping("/products/categories/{category}")
	public List<Product> getByCategory(@PathVariable String category)
	{
		return productService.getByCategory(category);
	}
	
	@GetMapping("/products/categories")
	public List<String> getCategories()
	{
		return productService.getCategories();
	}
}
