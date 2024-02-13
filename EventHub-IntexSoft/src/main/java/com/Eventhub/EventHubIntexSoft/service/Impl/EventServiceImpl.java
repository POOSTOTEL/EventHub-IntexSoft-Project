package com.Eventhub.EventHubIntexSoft.service.Impl;

import com.Eventhub.EventHubIntexSoft.dto.EventDto;
import com.Eventhub.EventHubIntexSoft.entity.Event;
import com.Eventhub.EventHubIntexSoft.mapper.EventMapper;
import com.Eventhub.EventHubIntexSoft.repository.EventRepository;
import com.Eventhub.EventHubIntexSoft.service.EventService;
import java.beans.FeatureDescriptor;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
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

  public EventDto createEvent(EventDto eventDto) {
    return EventMapper.instance.toEventDto(
        eventRepository.save(EventMapper.instance.toEvent(eventDto)));
  }

  public EventDto getEventByEventId(Long eventId) {
    return EventMapper.instance.toEventDto(eventRepository.findEventByEventId(eventId));
  }

  public EventDto updateEvent(EventDto eventDto) {
    Event event = eventRepository.findEventByEventId(eventDto.getEventId());
    BeanUtils.copyProperties(eventDto, event, "eventId");
    return EventMapper.instance.toEventDto(eventRepository.save(event));
  }

  public EventDto patchEvent(EventDto eventDto) {
    Event event = eventRepository.findEventByEventId(eventDto.getEventId());
    BeanUtils.copyProperties(eventDto, event, getNullProperties(eventDto));
    return EventMapper.instance.toEventDto(eventRepository.save(event));
  }

  public Event findEventByEventId(Long eventId) {
    return eventRepository.findEventByEventId(eventId);
  }

  @Transactional(isolation = Isolation.READ_COMMITTED)
  public void deleteEventByEventId(Long eventId) {
    eventRepository.deleteEventByEventId(eventId);
  }

  private String[] getNullProperties(EventDto eventDto) {
    final BeanWrapper wrapper = new BeanWrapperImpl(eventDto);
    return Stream.of(wrapper.getPropertyDescriptors())
        .map(FeatureDescriptor::getName)
        .filter(name -> wrapper.getPropertyValue(name) == null)
        .toArray(String[]::new);
  }
}
