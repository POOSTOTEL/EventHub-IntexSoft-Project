package com.Eventhub.EventHubIntexSoft.service.Impl;

import com.Eventhub.EventHubIntexSoft.dto.EventDto;
import com.Eventhub.EventHubIntexSoft.entity.Event;
import com.Eventhub.EventHubIntexSoft.mapper.EventMapper;
import com.Eventhub.EventHubIntexSoft.repository.EventRepository;
import com.Eventhub.EventHubIntexSoft.service.EventService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
  private final EventRepository eventRepository;

  public List<EventDto> getAllEvents() {
    return EventMapper.instance.toDtoList(eventRepository.findAll());
  }

  public Optional<EventDto> createEvent(Event event) {
    return Optional.ofNullable(EventMapper.instance.toEventDto(eventRepository.save(event)));
  }

  public Optional<EventDto> getEventByEventId(Long eventId) {
    return Optional.ofNullable(
        EventMapper.instance.toEventDto(eventRepository.findEventByEventId(eventId)));
  }

  public Optional<EventDto> updateEvent(EventDto eventDto) {
    return Optional.ofNullable(eventRepository.findEventByEventId(eventDto.getEventId()))
        .filter(
            event ->
                eventRepository.findAllByTitle(eventDto.getTitle()).isEmpty()
                    || event.getTitle().equals(eventDto.getTitle()))
        .map(
            event -> {
              Optional.ofNullable(event.getTitle()).ifPresent(event::setTitle);
              Optional.ofNullable(event.getDescription()).ifPresent(event::setDescription);
              Optional.ofNullable(event.getEventDate()).ifPresent(event::setEventDate);
              Optional.ofNullable(event.getLocation()).ifPresent(event::setLocation);
              return EventMapper.instance.toEventDto(eventRepository.save(event));
            });
  }

  @Named("findEventByEventId")
  public Event findEventByEventId(Long eventId) {
    return eventRepository.findEventByEventId(eventId);
  }

  @Transactional(isolation = Isolation.READ_COMMITTED)
  public boolean deleteEventByEventId(Long eventId) {
    return Optional.ofNullable(eventRepository.findEventByEventId(eventId))
        .map(
            event -> {
              eventRepository.deleteEventByEventId(eventId);
              return true;
            })
        .orElse(false);
  }
}
