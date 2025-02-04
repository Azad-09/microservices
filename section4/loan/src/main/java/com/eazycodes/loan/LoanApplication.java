package com.eazycodes.loan;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication()
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Loans Microservice REST API Documentation",
				description = "Eazycode Loans Microservice REST API",
				version = "v1",
				contact = @Contact(
						name = "Azad Mahapatra",
						email = "azad@gmail.com",
						url = "https://www.eazycodes.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.eazycodes.com"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Eazycodes Loans Microservice REST API Documentation",
				url = "http://eazycodes/swagger-ui/index.html	"
		)
)

public class LoanApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanApplication.class, args);
	}

}
