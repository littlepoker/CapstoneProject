package com.example.demo;

import java.util.List;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private Job job;
	
	public List<Product> fetchAllProducts() {
		return productRepository.findAll();
	}
	
	public Product addProduct(Product product) {
		return productRepository.save(product);
	}
	
	public Product getProduct(int id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Product does not Exist"));
	}
	
	public Product updateProduct(Product product, int id)
	{
		product.setId(id);
		return productRepository.save(product);
	}

	public void deleteProduct(int id)
	{
		productRepository.deleteById(id);
	}

	public List<Product> getByCategory(String category)
	{
		return productRepository.findAllByCategory(category);
	}

	public List<String> getCategories()
	{
		return productRepository.findCategories();
	}
	
	public void bulkUpload() {
		try {

			JobParameters jobParameters = new JobParametersBuilder().addLong("startAt", System.currentTimeMillis())
					.toJobParameters();

			JobExecution execution = jobLauncher.run(job, jobParameters);
			if (execution.getExitStatus().getExitCode().equals(ExitStatus.COMPLETED)) {
				System.out.println("batch uploaded");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
