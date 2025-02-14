package com.example.demo;

import java.io.File;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ProductConfig {
	private final ProductRepository productRepository;
	
	@Bean
	@StepScope
	public FlatFileItemReader<Product> itemReader() { 
		FlatFileItemReader<Product> flatFileItemReader = new FlatFileItemReader<Product>();
		flatFileItemReader.setResource(new FileSystemResource(new File("src/main/resources/products.csv")));
		flatFileItemReader.setName("csv-reader");
		flatFileItemReader.setLinesToSkip(1);
		flatFileItemReader.setLineMapper(lineMapper());
		return flatFileItemReader;
	}
	
	@Bean
	LineMapper<Product> lineMapper() {
		DefaultLineMapper<Product> lineMapper = new DefaultLineMapper<Product>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames("id", "title", "price", "description", "category", "image");
		
		BeanWrapperFieldSetMapper<Product> fieldSetMapper = new BeanWrapperFieldSetMapper<Product>();
		fieldSetMapper.setTargetType(Product.class);
		lineMapper.setLineTokenizer(lineTokenizer);
		lineMapper.setFieldSetMapper(fieldSetMapper);
		return lineMapper;
	}
	
	@Bean
	public ProductProcessor processor() {
		return new ProductProcessor();
	}
	
	public RepositoryItemWriter<Product> writer() {
		RepositoryItemWriter<Product> writer = new RepositoryItemWriter<Product>();
		writer.setRepository(productRepository);
		writer.setMethodName("save");
		return writer;
	}
	
	@Bean
	public Step step(ItemReader<Product> reader, ItemProcessor <Product, Product> processor,
			ItemWriter<Product> writer, JobRepository jobRepository, PlatformTransactionManager manager)
	{
		return new StepBuilder("mystep", jobRepository).<Product, Product>chunk(1, manager)
				.reader(reader)
				.processor(processor)
				.writer(writer)
				.build();	
	}
	
	@Bean
	public Job RunJob(Step step, JobRepository jobRepository) {
		return new JobBuilder("myjob", jobRepository).incrementer(new RunIdIncrementer())
				.flow(step).end().build();
	}
	
	@Bean
	public TaskExecutor taskExecutor() {
		SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
		taskExecutor.setConcurrencyLimit(10);
		return taskExecutor;
	}
}
