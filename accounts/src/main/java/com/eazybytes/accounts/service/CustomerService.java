package com.eazybytes.accounts.service;

import com.eazybytes.accounts.dto.CustomerDetailDTO;

public interface CustomerService {

  /**
   * Get customer details.
   *
   * @param mobileNumber the mobile number
   * @return the customer details
   */
  CustomerDetailDTO getCustomerDetails(String mobileNumber);
}
