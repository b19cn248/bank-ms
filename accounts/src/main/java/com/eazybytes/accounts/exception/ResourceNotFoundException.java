package com.eazybytes.accounts.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = org.springframework.http.HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
    super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
  }
}
