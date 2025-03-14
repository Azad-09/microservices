package com.eazycodes.accounts.service.client;

import com.eazycodes.accounts.dto.LoansDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoansFallback implements LoansFeignClient{
    @Override
    public ResponseEntity<LoansDto> fetchLoansDetails(String mobileNumber, String correlationId) {
        return null;
    }
}
