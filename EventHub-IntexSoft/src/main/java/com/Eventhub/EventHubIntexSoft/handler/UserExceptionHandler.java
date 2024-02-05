package com.Eventhub.EventHubIntexSoft.handler;

import com.Eventhub.EventHubIntexSoft.exception.EmailFormatException;
import com.Eventhub.EventHubIntexSoft.exception.EmptyDtoFieldException;
import com.Eventhub.EventHubIntexSoft.exception.NonUniqValueException;
import com.Eventhub.EventHubIntexSoft.exception.PasswordFormatException;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {
  @ExceptionHandler(PasswordFormatException.class)
  public ResponseEntity<ErrorMessage> passwordFormatException(PasswordFormatException exception) {
    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
        .body(new ErrorMessage(exception.getMessage()));
  }

  @ExceptionHandler(EmailFormatException.class)
  public ResponseEntity<ErrorMessage> emailFormatException(EmailFormatException exception) {
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
}
