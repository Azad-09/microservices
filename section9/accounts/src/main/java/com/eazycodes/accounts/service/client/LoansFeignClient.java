package com.eazycodes.accounts.service.client;

import com.eazycodes.accounts.dto.CardsDto;
import com.eazycodes.accounts.dto.LoansDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("loans")
public interface LoansFeignClient {

    @GetMapping(value = "/api/fetch", consumes = "application/json")
    public ResponseEntity<LoansDto> fetchLoansDetails(@RequestParam String mobileNumber,
                                                      @RequestHeader("eazybank-correlation-id") String correlationId);
}
