package com.eazycodes.accounts;

import com.eazycodes.accounts.dto.AccountsInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableConfigurationProperties(value = {AccountsInfoDto.class})
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(info = @Info(
		title = "Accounts Microservice REST API documentation",
		description = "EazyCodes Accounts Microservice REST API documentation",
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
		description = "EazyCodes Accounts Microservice REST API documentation",
		url = "http://eazycodes/swagger-ui/index.html"
))
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
