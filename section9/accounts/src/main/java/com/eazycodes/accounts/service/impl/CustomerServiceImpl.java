package com.eazycodes.accounts.service.impl;

import com.eazycodes.accounts.dto.AccountsDto;
import com.eazycodes.accounts.dto.CardsDto;
import com.eazycodes.accounts.dto.CustomerDetailsDto;
import com.eazycodes.accounts.dto.LoansDto;
import com.eazycodes.accounts.entity.Accounts;
import com.eazycodes.accounts.entity.Customer;
import com.eazycodes.accounts.exception.ResourceNotFoundException;
import com.eazycodes.accounts.mapper.AccountsMapper;
import com.eazycodes.accounts.mapper.CustomerMapper;
import com.eazycodes.accounts.repository.AccountsRepository;
import com.eazycodes.accounts.repository.CustomerRepository;
import com.eazycodes.accounts.service.ICustomerService;
import com.eazycodes.accounts.service.client.CardsFeignClient;
import com.eazycodes.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "Mobile Number", mobileNumber)
        );

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Accounts", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(new CustomerDetailsDto(),customer);
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsdto( new AccountsDto(), accounts));

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(mobileNumber);
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoansDetails(mobileNumber);;
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        return customerDetailsDto;
    }
}
