package com.Eventhub.EventHubIntexSoft.mapper;

import com.Eventhub.EventHubIntexSoft.DTO.CommentDto;
import com.Eventhub.EventHubIntexSoft.DTO.EventDto;
import com.Eventhub.EventHubIntexSoft.entity.Comment;
import com.Eventhub.EventHubIntexSoft.entity.Event;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.factory.Mappers.getMapper;
@Mapper(componentModel = "spring", uses = EventMapper.class)
public interface EventListMapper {
    EventListMapper instance = getMapper(EventListMapper.class);
    List<Event> toEventList(List<EventDto> dtoList);
    List<EventDto> toDtoList(List<Event> modelList);
}