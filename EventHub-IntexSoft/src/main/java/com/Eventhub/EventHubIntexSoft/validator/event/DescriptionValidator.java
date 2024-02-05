package com.Eventhub.EventHubIntexSoft.validator.event;

import com.Eventhub.EventHubIntexSoft.exception.EmptyDtoFieldException;
import com.Eventhub.EventHubIntexSoft.validator.FieldValidator;
import org.springframework.stereotype.Service;

@Service
public class DescriptionValidator implements FieldValidator {
  @Override
  public void validate(String description, Long eventId) throws EmptyDtoFieldException {
    validateNullField(description);
  }
}
