package com.eazybytes.accounts.service;

import com.eazybytes.accounts.dto.CustomerDTO;

public interface IAccountService {

  /**
   * Create account
   *
   * @param customerDTO
   */
  void createAccount(CustomerDTO customerDTO);


  /**
   * Fetch customer details
   *
   * @param mobileNumber
   * @return
   */
  CustomerDTO fetchCustomerDetails(String mobileNumber);

  /**
   * Update account
   *
   * @param customerDTO
   * @return boolean indicating if the update of Account was success or not
   */
  boolean updateAccount(CustomerDTO customerDTO);

  /**
   * Delete account
   *
   * @param mobileNumber
   * @return boolean indicating if the deletion of Account was success or not
   */
  boolean deleteAccount(String mobileNumber);
}
