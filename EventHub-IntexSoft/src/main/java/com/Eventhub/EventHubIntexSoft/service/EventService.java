package com.Eventhub.EventHubIntexSoft.service;

import com.Eventhub.EventHubIntexSoft.dto.EventDto;
import com.Eventhub.EventHubIntexSoft.entity.Event;
import java.util.List;
import java.util.Optional;

public interface EventService {
  List<EventDto> getAllEvents();

  Optional<EventDto> createEvent(EventDto eventDto);

  Optional<EventDto> getEventByEventId(Long eventId);

  Optional<EventDto> updateEvent(EventDto eventDto);

  boolean deleteEventByEventId(Long eventId);

  Event findEventByEventId(Long eventId);
}
