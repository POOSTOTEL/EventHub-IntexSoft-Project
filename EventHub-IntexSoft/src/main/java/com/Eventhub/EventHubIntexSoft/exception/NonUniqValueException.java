package com.Eventhub.EventHubIntexSoft.exception;

import jakarta.validation.ValidationException;

public class NonUniqValueException extends Exception {
    public NonUniqValueException() {
        super("Check the uniqueness of the entered values.");
    }
}
