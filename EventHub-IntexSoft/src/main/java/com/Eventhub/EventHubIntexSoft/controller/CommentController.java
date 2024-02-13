package com.Eventhub.EventHubIntexSoft.controller;

import com.Eventhub.EventHubIntexSoft.dto.CommentDto;
import com.Eventhub.EventHubIntexSoft.exception.EmptyDtoFieldException;
import com.Eventhub.EventHubIntexSoft.exception.FormatException;
import com.Eventhub.EventHubIntexSoft.exception.NotFoundException;
import com.Eventhub.EventHubIntexSoft.service.CommentService;
import com.Eventhub.EventHubIntexSoft.validator.CommentValidator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

  private final CommentService commentService;
  private final CommentValidator commentValidator;

  @GetMapping("/all")
  public ResponseEntity<List<CommentDto>> allComments() {
    return ResponseEntity.ok(commentService.getAllComments());
  }

  @PostMapping
  public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto)
      throws NotFoundException, EmptyDtoFieldException, FormatException {
    commentValidator.validateCommentDtoSave(commentDto);
    return new ResponseEntity<>(commentService.createComment(commentDto), HttpStatus.OK);
  }

  @GetMapping("/{commentId}")
  public ResponseEntity<CommentDto> getCommentByCommentId(@PathVariable("commentId") Long commentId)
      throws NotFoundException {
    commentValidator.validateCommentExistingByCommentId(commentId);
    return new ResponseEntity<>(commentService.getCommentByCommentId(commentId), HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto)
      throws NotFoundException, EmptyDtoFieldException, FormatException {
    commentValidator.validateCommentDtoUpdate(commentDto);
    return new ResponseEntity<>(commentService.updateComment(commentDto), HttpStatus.OK);
  }

  @PatchMapping
  public ResponseEntity<CommentDto> patchComment(@RequestBody CommentDto commentDto)
      throws EmptyDtoFieldException, NotFoundException, FormatException {
    commentValidator.validateCommentDtoPatch(commentDto);
    commentService.patchComment(commentDto);
    return new ResponseEntity<>(
        commentService.getCommentByCommentId(commentDto.getCommentId()), HttpStatus.OK);
  }

  @DeleteMapping("/{commentId}")
  public ResponseEntity<String> deleteCommentByCommentId(@PathVariable("commentId") Long commentId)
      throws NotFoundException {
    commentValidator.validateCommentExistingByCommentId(commentId);
    commentService.deleteCommentByCommentId(commentId);
    return ResponseEntity.ok("Comment with id " + commentId + " deleted.");
  }
}
