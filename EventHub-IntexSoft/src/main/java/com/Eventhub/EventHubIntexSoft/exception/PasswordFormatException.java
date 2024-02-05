package com.Eventhub.EventHubIntexSoft.exception;

public class PasswordFormatException extends Exception {
  public PasswordFormatException() {
    super("The password does not match the format, double-check the entered password.");
  }
}
