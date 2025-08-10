package com.eazycodes.accounts.service;

import com.eazycodes.accounts.dto.CustomerDetailsDto;
import org.springframework.stereotype.Service;

public interface ICustomerService {

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return
     */
    CustomerDetailsDto fetchCustomerDetails(String mobileNumber,String correlationId);
}
