package com.eazycodes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(
        name = "Customer",
        description = "Schema to hold Customer and Accounts information"
)
public class CustomerDto {
    @Schema(
            description = "Name of Customer",
            example = "Radhe Shyam"
    )
    @NotEmpty(message = "Name is a required field")
    @Size(min = 3, max = 30, message = "Name must contain 3 or more character")
    private String name;

    @Schema(
            description = "Email of Customer",
            example = "azad@gmail.com"
    )
    @NotEmpty(message = "Email is a required field")
    @Email(message = "Please enter a valid email address")
    private String email;

    @Schema(
            description = "Mobile number of Customer",
            example = "9040094831"
    )
    @NotEmpty(message = "Mobile number is a required field" )
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be of 10 digits")
    private String mobileNumber;

    @Schema(
            description = "Accounts details of the Customer"
    )
    private AccountsDto accountsDto;
}
