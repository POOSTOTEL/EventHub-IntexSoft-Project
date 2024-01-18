package com.Eventhub.EventHubIntexSoft.mapper;

import com.Eventhub.EventHubIntexSoft.DTO.EventDTO;
import com.Eventhub.EventHubIntexSoft.entity.Event;

public abstract class EventMapper {
    public static EventDTO toDTO (Event event) {
        return new EventDTO(event.getId(), event.getTitle(), event.getDescription(), event.getEventDate(), event.getLocation());
    }
    public static Event toEvent(EventDTO eventDTO) {
        return new Event(eventDTO.getId(), eventDTO.getTitle(), eventDTO.getDescription(), eventDTO.getEventDate(), eventDTO.getLocation());
    }
}
