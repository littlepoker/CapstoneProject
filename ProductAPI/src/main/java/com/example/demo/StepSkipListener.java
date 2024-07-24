package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;

public class StepSkipListener implements SkipListener<Product, Number>{
	public static Logger log = LoggerFactory.getLogger(StepSkipListener.class);
	
	@Override
	public void onSkipInProcess(Product item, Throwable t) {
		// TODO Auto-generated method stub
		log.info("skipped due to exception");
	}
	
	@Override
	public void onSkipInRead(Throwable t) {
		// TODO Auto-generated method stub
		log.info(t.getMessage());
	}
	
	@Override
	public void onSkipInWrite(Number item, Throwable t) {
		// TODO Auto-generated method stub
		log.info(t.getMessage());
	}
}
