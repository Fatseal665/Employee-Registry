package se.sti.employee_registry;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class EmployeeRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeRegistryApplication.class, args);
	}

}
