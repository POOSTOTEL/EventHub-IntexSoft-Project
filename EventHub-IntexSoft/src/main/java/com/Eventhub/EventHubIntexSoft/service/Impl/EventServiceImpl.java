package com.Eventhub.EventHubIntexSoft.service.Impl;

import com.Eventhub.EventHubIntexSoft.dto.EventDto;
import com.Eventhub.EventHubIntexSoft.entity.Event;
import com.Eventhub.EventHubIntexSoft.mapper.EventMapper;
import com.Eventhub.EventHubIntexSoft.repository.EventRepository;
import com.Eventhub.EventHubIntexSoft.service.EventService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
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

  public Optional<EventDto> createEvent(EventDto eventDto) {
    return Optional.ofNullable(
        EventMapper.instance.toEventDto(
            eventRepository.save(EventMapper.instance.toEvent(eventDto))));
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
              Optional.ofNullable(event.getTitle()).ifPresent(eventDto::setTitle);
              Optional.ofNullable(event.getDescription()).ifPresent(eventDto::setDescription);
              Optional.ofNullable(event.getEventDate()).ifPresent(eventDto::setEventDate);
              Optional.ofNullable(event.getLocation()).ifPresent(eventDto::setLocation);
              return EventMapper.instance.toEventDto(eventRepository.save(event));
            });
  }

  //  @Named("findEventByEventId")
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
