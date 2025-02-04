package com.eazycodes.accounts.service;

import com.eazycodes.accounts.dto.CustomerDto;

public interface IAccountsService {

    /**
     * Create a new account in the system.
     *
     * @param customerDto Customer info
     */
    void createAccount(CustomerDto customerDto);

    /**
     * Fetch account details from the system.
     *
     * @param mobileNumber Customer mobile number
     * @return Customer details
     */
    CustomerDto fetchAccount(String mobileNumber);

    /**
     * Update account details in the system.
     *
     * @param customerDto Customer info
     * @return boolean
     */
    boolean updateAccount(CustomerDto customerDto);

    /**
     * Delete account from the system.
     *
     * @param mobileNumber Customer mobile number
     * @return boolean
     */
    boolean deleteAccount(String mobileNumber);
}
