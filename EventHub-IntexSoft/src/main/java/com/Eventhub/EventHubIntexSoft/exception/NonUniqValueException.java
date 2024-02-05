package com.Eventhub.EventHubIntexSoft.exception;

public class NonUniqValueException extends Exception {
  public NonUniqValueException() {
    super("Check the uniqueness of the entered values.");
  }
}
