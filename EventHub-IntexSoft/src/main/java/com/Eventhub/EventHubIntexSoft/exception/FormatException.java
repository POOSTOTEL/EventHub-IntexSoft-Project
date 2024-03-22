package com.Eventhub.EventHubIntexSoft.exception;

public class FormatException extends Exception {
  public FormatException() {
    super("The email does not follow the established format, please double check the address.");
  }
}
