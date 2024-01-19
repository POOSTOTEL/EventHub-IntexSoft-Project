package com.Eventhub.EventHubIntexSoft.mapper;

import com.Eventhub.EventHubIntexSoft.DTO.EventDto;
import com.Eventhub.EventHubIntexSoft.entity.Event;
import org.mapstruct.Mapper;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventMapper instance = getMapper(EventMapper.class);

    EventDto toEventDto(Event event);

    Event toEvent(EventDto eventDto);
}
