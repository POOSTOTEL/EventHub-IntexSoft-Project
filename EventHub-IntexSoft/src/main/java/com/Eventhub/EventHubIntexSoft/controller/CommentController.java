package com.Eventhub.EventHubIntexSoft.controller;

import com.Eventhub.EventHubIntexSoft.dto.CommentDto;
import com.Eventhub.EventHubIntexSoft.exception.EmptyDtoFieldException;
import com.Eventhub.EventHubIntexSoft.exception.NotFoundException;
import com.Eventhub.EventHubIntexSoft.service.CommentService;
import com.Eventhub.EventHubIntexSoft.validator.CommentValidator;
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

  private final CommentService commentService;
  private final CommentValidator commentValidator;

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
    return ResponseEntity.ok(commentService.getAllComments());
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
        schema = @Schema(implementation = CommentDto.class))
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
  public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto)
      throws NotFoundException, EmptyDtoFieldException {
    commentValidator.validateCommentDtoSave(commentDto);
    return new ResponseEntity<>(commentService.createComment(commentDto), HttpStatus.OK);
  }

  @GetMapping("/{commentId}")
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
  public ResponseEntity<CommentDto> getCommentByCommentId(@PathVariable("commentId") Long commentId)
      throws NotFoundException {
    commentValidator.validateCommentExistingByCommentId(commentId);
    return new ResponseEntity<>(commentService.getCommentByCommentId(commentId), HttpStatus.OK);
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
        schema =
            @Schema(
                example =
                    "{\"commentId\":\"3\", "
                        + "\"eventId\": null, "
                        + "\"userId\": null, "
                        + "\"comment\":\"Awesome meeting!!!\", "
                        + "\"rating\": null, "
                        + "\"commentDate\": null}"))
  })
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Comment updated successfully",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema =
                      @Schema(
                          example =
                              "{\"commentId\":\"3\", "
                                  + "\"eventId\":\"2\", "
                                  + "\"userId\":\"4\", "
                                  + "\"comment\":\"Awesome meeting!!!\", "
                                  + "\"rating\":\"10\", "
                                  + "\"commentDate\":\"2024-01-30T09:20:36\"}"))
            }),
        @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content)
      })
  public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto)
      throws NotFoundException, EmptyDtoFieldException {
    commentValidator.validateCommentDtoUpdate(commentDto);
    return new ResponseEntity<>(commentService.updateComment(commentDto), HttpStatus.OK);
  }

  @PatchMapping
  public ResponseEntity<CommentDto> patchComment(@RequestBody CommentDto commentDto)
      throws EmptyDtoFieldException, NotFoundException {
    commentValidator.validateCommentDtoPatch(commentDto);
    commentService.patchComment(commentDto);
    return new ResponseEntity<>(
        commentService.getCommentByCommentId(commentDto.getCommentId()), HttpStatus.OK);
  }

  @DeleteMapping("/{commentId}")
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
  public ResponseEntity<String> deleteCommentByCommentId(@PathVariable("commentId") Long commentId)
      throws NotFoundException {
    commentValidator.validateCommentExistingByCommentId(commentId);
    commentService.deleteCommentByCommentId(commentId);
    return ResponseEntity.ok("Comment with id " + commentId + " deleted.");
  }
}
