package com.eazycodes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Schema(
        name = "Cards",
        description = "Schema to hold card information"
)
@Data
public class CardsDto {

    @NotEmpty
    @Pattern(regexp="(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    @Schema(
            description = "Mobile Number of Customer", example = "9040094829"
    )
    private String mobileNumber;

    @NotEmpty(message = "Card number can not be null or empty")
    @Pattern(regexp = "(^$|[0-9]{12})", message = "Card number must be of 12  digits")
    @Schema(
            description = "Card number of the customer", example = "990065437654"
    )
    private String cardNumber;

    @NotEmpty(message = "CardType can not be empty or null")
    @Schema(
            description = "Type of the card", example = "Credit card"
    )
    private String cardType;

    @Positive(message = "Total card limit should be greater than 0")
    @Schema(
            description = "Total amount limit available against a card", example = "10000"
    )
    private int totalLimit;

    @PositiveOrZero(message = "Total amount used should be equal or greater to zero")
    @Schema(
            description = "Total amount used by the customer", example = "1000"
    )
    private int amountUsed;

    @PositiveOrZero(message = "Total available amount should be equal or greater to zero")
    @Schema(
            description = "Total available amount against a card", example = "9000"
    )
    private int availableAmount;
}
