package com.Eventhub.EventHubIntexSoft.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentDTO {
    private Long id;
    private EventDTO eventDTO;
    private UserDTO userDTO;
    private String comment;
    private Integer rating;
    private LocalDateTime commentDate;
}