package com.typetaskpro.core.exceptions;

public class ImageServiceException extends RuntimeException {
  
  public ImageServiceException(String message) {
    super(message);
  }

  public ImageServiceException(String message, Throwable cause) {
    super(message, cause);
  }
}
