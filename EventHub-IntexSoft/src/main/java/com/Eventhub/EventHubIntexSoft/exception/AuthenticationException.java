package com.Eventhub.EventHubIntexSoft.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AuthenticationException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public AuthenticationException(String message) {
        super(String.format(message));
    }
}
