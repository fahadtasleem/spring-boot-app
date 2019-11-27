package org.fahad.spring.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.fahad"})
public class SchedulerApplication {
	public static void main(String[] args) {
		SpringApplication.run(SchedulerApplication.class,args);
	}
}
