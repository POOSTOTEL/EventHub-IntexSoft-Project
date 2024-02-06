package com.Eventhub.EventHubIntexSoft.exception;

import jakarta.validation.ValidationException;

public class EmptyDtoFieldException extends Exception {
  public EmptyDtoFieldException() {
    super("Some data field appeared empty.");
  }
}
