package com.Eventhub.EventHubIntexSoft.exception;

public class EmptyDtoFieldException extends Exception {
  public EmptyDtoFieldException() {
    super("Some data field appeared empty.");
  }
}
