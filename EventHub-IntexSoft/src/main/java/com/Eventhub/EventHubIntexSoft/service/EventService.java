package com.Eventhub.EventHubIntexSoft.service;

import com.Eventhub.EventHubIntexSoft.dto.EventDto;
import com.Eventhub.EventHubIntexSoft.entity.Event;
import java.util.List;

public interface EventService {
  List<EventDto> getAllEvents();

  EventDto createEvent(EventDto eventDto);

  EventDto getEventByEventId(Long eventId);

  EventDto updateEvent(EventDto eventDto);

  EventDto patchEvent(EventDto eventDto);

  void deleteEventByEventId(Long eventId);

  Event findEventByEventId(Long eventId);
}
