package com.Eventhub.EventHubIntexSoft.service;

import com.Eventhub.EventHubIntexSoft.dto.EventDto;
import com.Eventhub.EventHubIntexSoft.entity.Event;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EventService {
  Page<EventDto> getAllEvents(Integer offset, Integer limit);

  EventDto createEvent(EventDto eventDto);

  EventDto getEventByEventId(Long eventId);

  EventDto updateEvent(EventDto eventDto);

  EventDto patchEvent(EventDto eventDto);

  void deleteEventByEventId(Long eventId);

  Event findEventByEventId(Long eventId);
}
