package com.Eventhub.EventHubIntexSoft.handler;

import com.Eventhub.EventHubIntexSoft.exception.EmptyDtoFieldException;
import com.Eventhub.EventHubIntexSoft.exception.FormatException;
import com.Eventhub.EventHubIntexSoft.exception.NonUniqValueException;
import com.Eventhub.EventHubIntexSoft.exception.NotFoundException;
import com.Eventhub.EventHubIntexSoft.exception.TokenRefreshException;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class UserExceptionHandler {
  @ExceptionHandler(FormatException.class)
  public ResponseEntity<ErrorMessage> formatException(FormatException exception) {
    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
        .body(new ErrorMessage(exception.getMessage()));
  }

  @ExceptionHandler(EmptyDtoFieldException.class)
  public ResponseEntity<ErrorMessage> emptyDtoFieldException(EmptyDtoFieldException exception) {
    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
        .body(new ErrorMessage(exception.getMessage()));
  }

  @ExceptionHandler(NonUniqValueException.class)
  public ResponseEntity<ErrorMessage> nonUniqValueException(NonUniqValueException exception) {
    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
        .body(new ErrorMessage(exception.getMessage()));
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorMessage> notFoundException(NotFoundException exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ErrorMessage(exception.getMessage()));
  }

  @ExceptionHandler(value = TokenRefreshException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ErrorMessage handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
    return new ErrorMessage(ex.getMessage());
  }
}
