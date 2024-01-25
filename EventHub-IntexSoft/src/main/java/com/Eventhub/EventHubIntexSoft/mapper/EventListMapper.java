package com.Eventhub.EventHubIntexSoft.mapper;

import static org.mapstruct.factory.Mappers.getMapper;

import com.Eventhub.EventHubIntexSoft.DTO.EventDto;
import com.Eventhub.EventHubIntexSoft.entity.Event;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface EventListMapper {
  EventListMapper instance = getMapper(EventListMapper.class);

  List<Event> toEventList(List<EventDto> dtoList);

  List<EventDto> toDtoList(List<Event> modelList);
}
