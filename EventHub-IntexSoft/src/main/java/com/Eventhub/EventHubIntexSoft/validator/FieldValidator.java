package com.Eventhub.EventHubIntexSoft.validator;

import com.Eventhub.EventHubIntexSoft.exception.EmailFormatException;
import com.Eventhub.EventHubIntexSoft.exception.EmptyDtoFieldException;
import com.Eventhub.EventHubIntexSoft.exception.NonUniqValueException;
import com.Eventhub.EventHubIntexSoft.exception.NotFoundException;
import com.Eventhub.EventHubIntexSoft.exception.PasswordFormatException;

import java.util.Objects;

public abstract class FieldValidator {
  public void validate(String field, Long id) throws EmptyDtoFieldException, NonUniqValueException, EmailFormatException {}

  public void validate(Integer field, Long id){}

  public void validate(Long field, Long id){}

  public void validate (String field) throws EmptyDtoFieldException, PasswordFormatException {}
  public void validate (Integer field) throws EmptyDtoFieldException {}

  public void validate(Long userId) throws NotFoundException, EmptyDtoFieldException {}

  public <T> void validateNullField(T value) throws EmptyDtoFieldException {
    if (Objects.isNull(value)) {
      throw new EmptyDtoFieldException();
    }
  }
}
