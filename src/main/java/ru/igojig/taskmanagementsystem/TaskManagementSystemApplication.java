package ru.igojig.taskmanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.igojig.taskmanagementsystem.configuration.RsaProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaProperties.class)
public class TaskManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagementSystemApplication.class, args);
	}

}
