package com.eazycodes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "Accounts",
        description = "Schema to hold Accounts information"
)
public class AccountsDto {
    @Schema(
            description = "Accounts number of EazyCodes bank",
            example = "2233456789"
    )
    @NotEmpty(message = "Account number is a required field")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Account number must contain 10 digits")
    private Long accountNumber;

    @Schema(
            description = "Accounts type of EazyCodes bank",
            example = "savings"
    )
    @NotEmpty(message = "Account type is a required field")
    private String accountType;

    @Schema(
            description = "Address of EazyCodes bank",
            example = ""
    )
    @NotEmpty(message = "Address is a required field")
    private String address;
}
