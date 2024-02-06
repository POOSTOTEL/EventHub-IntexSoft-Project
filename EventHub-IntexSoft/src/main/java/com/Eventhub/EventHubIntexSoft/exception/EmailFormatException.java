package com.Eventhub.EventHubIntexSoft.exception;

import jakarta.validation.ValidationException;

public class EmailFormatException extends Exception {
  public EmailFormatException() {
    super("The email does not follow the established format, please double check the address.");
  }
}
