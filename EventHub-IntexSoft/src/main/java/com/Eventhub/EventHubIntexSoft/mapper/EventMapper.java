package com.Eventhub.EventHubIntexSoft.mapper;

import static org.mapstruct.factory.Mappers.getMapper;

import com.Eventhub.EventHubIntexSoft.dto.EventDto;
import com.Eventhub.EventHubIntexSoft.entity.Event;
import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface EventMapper {
  EventMapper instance = getMapper(EventMapper.class);

  EventDto toEventDto(Event event);

  Event toEvent(EventDto eventDto);

  List<Event> toEventList(List<EventDto> dtoList);

  List<EventDto> toDtoList(List<Event> modelList);
}
