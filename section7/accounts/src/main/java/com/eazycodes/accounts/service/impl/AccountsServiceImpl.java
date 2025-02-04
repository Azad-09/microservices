package com.eazycodes.accounts.service.impl;

import com.eazycodes.accounts.constants.AccountsConstants;
import com.eazycodes.accounts.dto.AccountsDto;
import com.eazycodes.accounts.dto.CustomerDto;
import com.eazycodes.accounts.entity.Accounts;
import com.eazycodes.accounts.entity.Customer;
import com.eazycodes.accounts.exception.CustomerAlreadyExistException;
import com.eazycodes.accounts.exception.ResourceNotFoundException;
import com.eazycodes.accounts.mapper.AccountsMapper;
import com.eazycodes.accounts.mapper.CustomerMapper;
import com.eazycodes.accounts.repository.AccountsRepository;
import com.eazycodes.accounts.repository.CustomerRepository;
import com.eazycodes.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = this.customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if (optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistException("Customer already registered with given Mobile Number: " +
                    customerDto.getMobileNumber());
        }
        Customer savedCustomer = this.customerRepository.save(customer);
        this.accountsRepository.save(createNewAccount(savedCustomer));
    }


    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = this.customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "Mobile Number", mobileNumber));

        Accounts accounts = this.accountsRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Accounts", "Customer Id", customer.getCustomerId().toString()));

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(new CustomerDto(), customer);
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsdto(new AccountsDto(), accounts));

        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();

        if (accountsDto != null) {
            Accounts accounts = this.accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow( () ->
                     new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );

            AccountsMapper.mapToAccounts(accounts, accountsDto);
            accounts = this.accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();

            Customer customer = this.customerRepository.findById(customerId).orElseThrow(() ->
                    new ResourceNotFoundException("Customer", "CustomerId", customerId.toString()));

            CustomerMapper.mapToCustomer(customerDto, customer);
            this.customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = this.customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "Mobile Number", mobileNumber)
        );
        this.customerRepository.deleteById(customer.getCustomerId());
        this.accountsRepository.deleteByCustomerId(customer.getCustomerId());
        return true;
    }


}
