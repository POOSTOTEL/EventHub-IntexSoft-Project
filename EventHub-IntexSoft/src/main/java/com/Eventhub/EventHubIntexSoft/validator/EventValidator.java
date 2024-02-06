package com.Eventhub.EventHubIntexSoft.validator;

import com.Eventhub.EventHubIntexSoft.dto.EventDto;
import com.Eventhub.EventHubIntexSoft.entity.Event;
import com.Eventhub.EventHubIntexSoft.exception.EmptyDtoFieldException;
import com.Eventhub.EventHubIntexSoft.exception.NonUniqValueException;
import com.Eventhub.EventHubIntexSoft.exception.NotFoundException;
import com.Eventhub.EventHubIntexSoft.repository.EventRepository;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventValidator {
  @Autowired protected EventRepository eventRepository;

  String dateTimePattern = "dd-MM-yyyy HH:mm:ss";
  DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern);

  public class DescriptionValidator extends FieldValidator {
    @Override
    public void validate(String description) throws EmptyDtoFieldException {
      validateNullField(description);
    }
  }

  public class TitleValidator extends FieldValidator {
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

  public class EventDateValidator extends FieldValidator {
    @Override
    public void validate(String eventDate) throws EmptyDtoFieldException {
      validateNullField(eventDate);
    }
  }

  public class LocationValidator extends FieldValidator {
    @Override
    public void validate(String location) throws EmptyDtoFieldException {
      validateNullField(location);
    }
  }

  public void validateEventDtoSave(EventDto eventDto)
      throws EmptyDtoFieldException, NonUniqValueException {
    new TitleValidator().validate(eventDto.getTitle(), eventDto.getEventId());
    new DescriptionValidator().validate(eventDto.getDescription());
    new EventDateValidator().validate(eventDto.getEventDate().format(dateTimeFormatter));
    new LocationValidator().validate(eventDto.getLocation());
  }

  public void validateEventDtoUpdate(EventDto eventDto)
      throws EmptyDtoFieldException, NonUniqValueException, NotFoundException {
    validateEventExistingByEventId(eventDto.getEventId());
    validateEventDtoSave(eventDto);
  }

  public void validateEventDtoPatch(EventDto eventDto)
      throws NonUniqValueException, NotFoundException, EmptyDtoFieldException {
    validateEventExistingByEventId(eventDto.getEventId());
    if (Objects.nonNull(eventDto.getTitle())) {
      new TitleValidator().validate(eventDto.getTitle(), eventDto.getEventId());
    }
    if (Objects.nonNull(eventDto.getDescription())) {
      new DescriptionValidator().validate(eventDto.getDescription());
    }
    if (Objects.nonNull(eventDto.getEventDate())) {
      new EventDateValidator().validate(eventDto.getEventDate().format(dateTimeFormatter));
    }
    if (Objects.nonNull(eventDto.getLocation())) {
      new LocationValidator().validate(eventDto.getLocation());
    }
  }

  public void validateEventExistingByEventId(Long eventId) throws NotFoundException {
    if (Objects.isNull(eventRepository.findEventByEventId(eventId))) {
      throw new NotFoundException();
    }
  }
}
