package com.huce.edu.configs;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.client.RestTemplate;

@Component
@Profile("!test")
public class AppStartupRunner implements ApplicationRunner {

	// [Constructor with injected dependencies]

	@Transactional
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// [Make the calls]

		RestTemplate restTemplate = new RestTemplate();
		//url get api lan dau tien
		restTemplate.getForObject("http://localhost:8080/api/level/getAll", String.class);
		restTemplate.getForObject("http://localhost:8080/api/topic/getTopicByLid", String.class);

		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	}
}