package com.Eventhub.EventHubIntexSoft.controller;

import com.Eventhub.EventHubIntexSoft.DTO.CommentDto;
import com.Eventhub.EventHubIntexSoft.entity.Comment;
import com.Eventhub.EventHubIntexSoft.service.Impl.CommentServiceImpl;
import java.util.List;
import java.util.Optional;
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
    return new ResponseEntity<>(
        commentServiceImpl.createComment(comment).get(), HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Optional<CommentDto>> getCommentById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(commentServiceImpl.getCommentById(id));
  }

  @PutMapping
  public ResponseEntity<Optional<CommentDto>> updateComment(@RequestBody CommentDto commentDto) {
    return ResponseEntity.ok(commentServiceImpl.updateComment(commentDto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteComment(@PathVariable("id") Long id) {
    commentServiceImpl.deleteCommentById(id);
    return ResponseEntity.ok("Comment with id " + id + " deleted.");
  }
}
