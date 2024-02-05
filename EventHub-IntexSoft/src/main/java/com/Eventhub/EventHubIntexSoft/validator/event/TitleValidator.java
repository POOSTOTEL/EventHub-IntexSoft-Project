package com.Eventhub.EventHubIntexSoft.validator.event;

import com.Eventhub.EventHubIntexSoft.entity.Event;
import com.Eventhub.EventHubIntexSoft.exception.EmptyDtoFieldException;
import com.Eventhub.EventHubIntexSoft.exception.NonUniqValueException;
import com.Eventhub.EventHubIntexSoft.repository.EventRepository;
import com.Eventhub.EventHubIntexSoft.validator.FieldValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
@Service
public class TitleValidator implements FieldValidator {
  @Autowired
  EventRepository eventRepository;
  @Override
  public void validate(String title, Long eventId)
      throws EmptyDtoFieldException, NonUniqValueException {
    validateNullField(title);
    validateUniqTitle(title, eventId);
  }

  public void validateUniqTitle(String title, Long eventId) throws NonUniqValueException {
    Event event = eventRepository.findEventByTitle(title);
    if (Objects.nonNull(event) && !event.getEventId().equals(eventId)) {
      throw new NonUniqValueException();
    }
  }
}
