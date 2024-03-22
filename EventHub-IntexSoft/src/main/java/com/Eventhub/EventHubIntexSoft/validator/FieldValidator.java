package com.Eventhub.EventHubIntexSoft.validator;

import com.Eventhub.EventHubIntexSoft.exception.EmptyDtoFieldException;
import java.util.Objects;

public abstract class FieldValidator {
  public <T> void validateNullField(T value) throws EmptyDtoFieldException {
    if (Objects.isNull(value)) {
      throw new EmptyDtoFieldException();
    }
  }
}
