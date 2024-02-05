package com.Eventhub.EventHubIntexSoft.validator.event;

import com.Eventhub.EventHubIntexSoft.exception.EmptyDtoFieldException;
import com.Eventhub.EventHubIntexSoft.validator.FieldValidator;
import org.springframework.stereotype.Service;

@Service
public class LocationValidator implements FieldValidator {
    @Override
    public void validate(String location, Long eventId) throws EmptyDtoFieldException {
        validateNullField(location);
    }
}
