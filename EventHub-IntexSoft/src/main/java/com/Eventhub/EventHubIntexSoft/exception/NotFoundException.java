package com.Eventhub.EventHubIntexSoft.exception;

public class NotFoundException extends Exception {
    public NotFoundException() {
        super("The data you requested was not found.");
    }
}
