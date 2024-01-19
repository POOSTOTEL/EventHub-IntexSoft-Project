package com.Eventhub.EventHubIntexSoft.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private EventDto eventDTO;
    private UserDto userDTO;
    private String comment;
    private Integer rating;
    private LocalDateTime commentDate;
}