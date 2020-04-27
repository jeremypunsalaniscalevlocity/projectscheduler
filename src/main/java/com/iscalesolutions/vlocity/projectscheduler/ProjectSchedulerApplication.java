package com.iscalesolutions.vlocity.projectscheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.iscalesolutions.vlocity.projectscheduler")
@SpringBootApplication
public class ProjectSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectSchedulerApplication.class, args);
	}

}
