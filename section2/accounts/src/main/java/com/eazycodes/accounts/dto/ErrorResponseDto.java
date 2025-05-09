package com.eazycodes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Schema(
        name = "ErrorResponse",
        description = "Schema to hold error response information"
)
@Data @AllArgsConstructor
public class ErrorResponseDto {
    @Schema(
            description = "API path invoked by Client"
    )
    private String apiPath;

    @Schema(
            description = "Error code representing Error happened "
    )
    private HttpStatus errorCode;

    @Schema(
            description = "Error message representing Error happened "
    )
    private String errorMessage;

    @Schema(
            description = "Time representing when error happened"
    )
    private LocalDateTime errorTime;
}
