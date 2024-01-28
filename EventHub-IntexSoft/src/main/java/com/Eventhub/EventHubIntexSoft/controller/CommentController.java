package com.Eventhub.EventHubIntexSoft.controller;

import com.Eventhub.EventHubIntexSoft.dto.CommentDto;
import com.Eventhub.EventHubIntexSoft.entity.Comment;
import com.Eventhub.EventHubIntexSoft.service.Impl.CommentServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "CommentController", description = "Provides CRUD functionality for the Comment entity")
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
  private final CommentServiceImpl commentServiceImpl;

  @GetMapping("/all")
  @Operation(
      summary = "Get all comments",
      description = "Fetch all comments and return them in a list.")
  @Parameters()
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successful operation",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CommentDto.class))
            })
      })
  public ResponseEntity<List<CommentDto>> allComments() {
    return ResponseEntity.ok(commentServiceImpl.getAllComments());
  }

  @PostMapping
  @Operation(
      summary = "Create a new comment",
      description =
          "Create a new comment and return the created comment if successful, or a conflict status if not successful.")
  @Parameters({
    @Parameter(
        name = "comment",
        required = true,
        description = "Comment to be created",
        schema = @Schema(implementation = Comment.class))
  })
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Comment created successfully",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CommentDto.class))
            }),
        @ApiResponse(
            responseCode = "409",
            description = "Comment creation failed due to conflict",
            content = @Content)
      })
  public ResponseEntity<CommentDto> createComment(@RequestBody Comment comment) {
    return commentServiceImpl
        .createComment(comment)
        .map(commentDto -> new ResponseEntity<>(commentDto, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));
  }

  @GetMapping("/{id}")
  @Operation(
      summary = "Get a comment by ID",
      description =
          "Fetch a comment by their ID and return the comment if found, or a not found status if not found.")
  @Parameters({
    @Parameter(
        name = "id",
        required = true,
        description = "ID of the comment to be fetched",
        schema = @Schema(type = "integer", format = "int64"))
  })
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Comment found successfully",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CommentDto.class))
            }),
        @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content)
      })
  public ResponseEntity<CommentDto> getCommentById(@PathVariable("id") Long commentId) {
    return commentServiceImpl
        .getCommentByCommentId(commentId)
        .map(commentDto -> new ResponseEntity<>(commentDto, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PutMapping
  @Operation(
      summary = "Update a comment",
      description =
          "Update a comment and return the updated comment if successful, or a not found status if not successful.")
  @Parameters({
    @Parameter(
        name = "commentDto",
        required = true,
        description = "CommentDto to be updated",
        schema = @Schema(implementation = CommentDto.class))
  })
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Comment updated successfully",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CommentDto.class))
            }),
        @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content)
      })
  public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto) {
    return commentServiceImpl
        .updateComment(commentDto)
        .map(
            commentDataTransferObject ->
                new ResponseEntity<>(commentDataTransferObject, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("/{id}")
  @Operation(
      summary = "Delete a comment by ID",
      description =
          "Delete a comment by their ID and return a success message if successful, or a not found status if not successful.")
  @Parameters({
    @Parameter(
        name = "id",
        required = true,
        description = "ID of the comment to be deleted",
        schema = @Schema(type = "integer", format = "int64"))
  })
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Comment deleted successfully",
            content = {@Content(mediaType = "text/plain", schema = @Schema(type = "string"))}),
        @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content)
      })
  public ResponseEntity<String> deleteComment(@PathVariable("id") Long commentId) {
    return commentServiceImpl.deleteCommentByCommentId(commentId)
        ? ResponseEntity.ok("Comment with id " + commentId + " deleted.")
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
