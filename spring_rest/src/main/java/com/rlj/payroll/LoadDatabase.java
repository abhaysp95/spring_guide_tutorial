package com.rlj.payroll;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

	private static Logger logger = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	public CommandLineRunner initDatabase(EmployeeRepository repository) {
		return args -> {
			logger.info("Preloading " + repository.save(new Employee("Biblo Baggins", "Burglar")));
			logger.info("Preloading " + repository.save(new Employee("Biblo Baggins", "Burglar")));
		};
	}
}
