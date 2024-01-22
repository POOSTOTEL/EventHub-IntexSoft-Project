package com.Eventhub.EventHubIntexSoft.service;

import com.Eventhub.EventHubIntexSoft.DTO.CommentDto;
import com.Eventhub.EventHubIntexSoft.DTO.EventDto;
import com.Eventhub.EventHubIntexSoft.entity.Comment;
import com.Eventhub.EventHubIntexSoft.entity.Event;
import com.Eventhub.EventHubIntexSoft.mapper.CommentListMapper;
import com.Eventhub.EventHubIntexSoft.mapper.CommentMapper;
import com.Eventhub.EventHubIntexSoft.mapper.EventListMapper;
import com.Eventhub.EventHubIntexSoft.mapper.EventMapper;
import com.Eventhub.EventHubIntexSoft.repository.CommentRepository;
import com.Eventhub.EventHubIntexSoft.repository.EventRepository;
import com.Eventhub.EventHubIntexSoft.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    public List<CommentDto> getAllComments() {return CommentListMapper.instance.toDtoList(commentRepository.findAll());}
    public Optional<CommentDto> createComment(Comment comment) {
        return Optional.ofNullable(CommentMapper.instance.toCommentDto(commentRepository.save(comment)));
    }
    public Optional<CommentDto> getComment(Long id) {
        return Optional.ofNullable(CommentMapper.instance.toCommentDto(commentRepository.findCommentById(id)));
    }
    public Optional<CommentDto> updateComment(Comment comment) {
        Comment commentFromDB = commentRepository.findCommentById(comment.getId());
        if(comment.getComment() != null) {
            commentFromDB.setComment(comment.getComment());
        }
        if(comment.getRating() != null) {
            commentFromDB.setRating(comment.getRating());
        }
        return Optional.of(CommentMapper.instance.toCommentDto(commentRepository.save(commentFromDB)));
    }
    @Transactional
    public void deleteComment(Long id) {
        commentRepository.deleteCommentById(id);
    }
}
