package com.eazycodes.cards.controller;

import com.eazycodes.cards.constants.CardConstants;
import com.eazycodes.cards.dto.CardsDto;
import com.eazycodes.cards.dto.CardsInfoDto;
import com.eazycodes.cards.dto.ErrorResponseDto;
import com.eazycodes.cards.dto.ResponseDto;
import com.eazycodes.cards.service.ICardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@Tag(
        name = "CRUD REST APIs for Cards Microservice",
        description = "CRUD REST APIs in EazyCode to CREATE, UPDATE, FETCH, and DELETE card details"
)
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class CardsController {

    public static final Logger logger = LoggerFactory.getLogger(CardsController.class);

    private final ICardService iCardService;

    public CardsController(ICardService iCardService) {
        this.iCardService = iCardService;
    }

    @Autowired
    private CardsInfoDto cardsInfoDto;

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Operation(
            summary = "Create card REST API",
            description = "REST API to create new Card inside EazyCode"
    )

    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCard(@Valid @RequestParam
                                                  @Pattern(regexp="(^$|[0-9]{10})", message = "Mobile number must be of 10 digit")
                                                  String mobileNumber){
        iCardService.createCard(mobileNumber);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(CardConstants.STATUS_201, CardConstants.MESSAGE_201));

    }

    @Operation(
            summary = "Fetch card details REST API",
            description = "FETCH API to fetch card details based on mobile number"
    )

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP status INTERNAL SERVER ERROR"
            )
    }
    )
    @GetMapping("/fetch")
    public ResponseEntity<CardsDto> fetchCardDetails(@Valid @RequestParam
                                                         @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number should be of 10 digit") String mobileNumber,
                                                     @RequestHeader("eazybank-correlation-id") String correlationId){
        logger.info("eazybanks correlation id found: {}", correlationId);
        CardsDto cardsDto = iCardService.fetchCard(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(cardsDto);
    }

    @Operation(
            summary = "Update card details REST API",
            description = "REST API to update card details based on a card number"
    )

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expection failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP status INTERNAL SERVER ERROR"
            )
    })

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCardDetails(@Valid @RequestBody CardsDto cardsDto){
        boolean isUpdated = iCardService.updateCard(cardsDto);

        if (isUpdated){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(CardConstants.STATUS_200, CardConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(CardConstants.STATUS_417, CardConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "Delete card details REST API",
            description = "REST API to delete card details based on a mobile number"
    )

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expection failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP status INtRNAL SERVER ERROR"
            )
    })

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCardDetails(@RequestParam @Valid
                                                             @Pattern(regexp="(^$|[0-9]{10})", message = "Mobile number must be of 10 digits") String mobileNumber){
        boolean isDeleted = iCardService.deleteCard(mobileNumber);

        if (isDeleted){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(CardConstants.STATUS_200, CardConstants.MESSAGE_200));
        }else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(CardConstants.STATUS_417, CardConstants.MESSAGE_417_DELETE));
        }
    }

    @Operation(
            summary = "Fetch Cards Information",
            description = "Get card information that is deployed with cards microservice"
    )

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP status INTERNAL SERVER ERROR"
            )
    })
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
    }

    @Operation(
            summary = "Fetch Build Information",
            description = "Get build information that is deployed with cards microservice"
    )

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP status INTERNAL SERVER ERROR"
            )
    })
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInformation(){
        return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
    }

    @Operation(
            summary = "Fetch Cards Information",
            description = "Get card information that is deployed with cards microservice"
    )

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP status INTERNAL SERVER ERROR"
            )
    })

    @GetMapping("/cards-info")
    public ResponseEntity<CardsInfoDto> getCardsInformation(){
        return ResponseEntity.status(HttpStatus.OK).body(cardsInfoDto);
    }
}
