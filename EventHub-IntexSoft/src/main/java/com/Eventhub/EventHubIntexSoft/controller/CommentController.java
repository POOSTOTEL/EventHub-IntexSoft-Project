package com.Eventhub.EventHubIntexSoft.controller;

import com.Eventhub.EventHubIntexSoft.DTO.CommentDto;
import com.Eventhub.EventHubIntexSoft.entity.Comment;
import com.Eventhub.EventHubIntexSoft.service.Impl.CommentServiceImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
  private final CommentServiceImpl commentServiceImpl;

  @GetMapping("/all")
  public ResponseEntity<List<CommentDto>> allComments() {
    return ResponseEntity.ok(commentServiceImpl.getAllComments());
  }

  @PostMapping
  public ResponseEntity<CommentDto> createComment(@RequestBody Comment comment) {
    return commentServiceImpl
        .createComment(comment)
        .map(commentDto -> new ResponseEntity<>(commentDto, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));
  }

  @GetMapping("/{id}")
  public ResponseEntity<CommentDto> getCommentById(@PathVariable("id") Long commentId) {
    return commentServiceImpl
        .getCommentByCommentId(commentId)
        .map(commentDto -> new ResponseEntity<>(commentDto, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PutMapping
  public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto) {
    return commentServiceImpl
        .updateComment(commentDto)
        .map(
            commentDataTransferObject ->
                new ResponseEntity<>(commentDataTransferObject, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteComment(@PathVariable("id") Long commentId) {
    return commentServiceImpl.deleteCommentByCommentId(commentId)
        ? ResponseEntity.ok("Comment with id " + commentId + " deleted.")
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
