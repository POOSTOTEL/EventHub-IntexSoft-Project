package com.Eventhub.EventHubIntexSoft.service;

import com.Eventhub.EventHubIntexSoft.DTO.EventDto;
import com.Eventhub.EventHubIntexSoft.entity.Event;
import java.util.List;
import java.util.Optional;

public interface EventService {
  List<EventDto> getAllEvents();

  Optional<EventDto> createEvent(Event event);

  Optional<EventDto> getEventById(Long id);

  Optional<EventDto> updateEvent(EventDto eventDto);

  boolean deleteEventById(Long id);
}
