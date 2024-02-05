package com.Eventhub.EventHubIntexSoft.validator;

import com.Eventhub.EventHubIntexSoft.exception.EmailFormatException;
import com.Eventhub.EventHubIntexSoft.exception.EmptyDtoFieldException;
import com.Eventhub.EventHubIntexSoft.exception.NonUniqValueException;

import java.util.Objects;

public interface FieldValidator {
    void validate(String field, Long id) throws Exception;
    default <T> void validateNullField(T value) throws EmptyDtoFieldException {
        if (Objects.isNull(value)) {
            throw new EmptyDtoFieldException();
        }
    }
}

