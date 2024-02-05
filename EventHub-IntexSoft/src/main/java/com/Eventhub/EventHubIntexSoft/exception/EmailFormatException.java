package com.Eventhub.EventHubIntexSoft.exception;

public class EmailFormatException extends Exception {
  public EmailFormatException() {
    super("The email does not follow the established format, please double check the address.");
  }
}
