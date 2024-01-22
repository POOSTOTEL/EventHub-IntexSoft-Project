package com.Eventhub.EventHubIntexSoft.service;

import com.Eventhub.EventHubIntexSoft.DTO.EventDto;
import com.Eventhub.EventHubIntexSoft.DTO.UserDto;
import com.Eventhub.EventHubIntexSoft.entity.Event;
import com.Eventhub.EventHubIntexSoft.mapper.EventListMapper;
import com.Eventhub.EventHubIntexSoft.mapper.EventMapper;
import com.Eventhub.EventHubIntexSoft.repository.CommentRepository;
import com.Eventhub.EventHubIntexSoft.repository.EventRepository;
import com.Eventhub.EventHubIntexSoft.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.spec.OAEPParameterSpec;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final ParticipantRepository participantRepository;
    private final CommentRepository commentRepository;
    public List<EventDto> getAllEvents() {return EventListMapper.instance.toDtoList(eventRepository.findAll());}
    public Optional<EventDto> createEvent(Event event) {
        return Optional.ofNullable(EventMapper.instance.toEventDto(eventRepository.save(event)));
    }
    public Optional<EventDto> getEvent(Long id) {
        return Optional.ofNullable(EventMapper.instance.toEventDto(eventRepository.findEventById(id)));
    }
    public Optional<EventDto> updateEvent(Event event) {
        Event eventFromDB = eventRepository.findEventById(event.getId());
        if (event.getTitle() != null) {
            eventFromDB.setTitle(event.getTitle());
        }
        if (event.getDescription() != null) {
            eventFromDB.setDescription(event.getDescription());
        }
        if (event.getEventDate() != null) {
            eventFromDB.setEventDate(event.getEventDate());
        }
        if (event.getLocation() != null) {
            eventFromDB.setLocation(event.getLocation());
        }
        return Optional.of(EventMapper.instance.toEventDto(eventRepository.save(eventFromDB)));
    }
    @Transactional
    public void deleteEvent(Long id) {
        commentRepository.deleteAllByEventId(id);
        participantRepository.deleteAllByEventId(id);
        eventRepository.deleteEventById(id);
    }
}
