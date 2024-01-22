package com.Eventhub.EventHubIntexSoft.controller;

import com.Eventhub.EventHubIntexSoft.DTO.CommentDto;
import com.Eventhub.EventHubIntexSoft.entity.Comment;
import com.Eventhub.EventHubIntexSoft.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    @GetMapping("/all")
    public ResponseEntity<List<CommentDto>> allComments() {
        return ResponseEntity.ok(commentService.getAllComments());
    }

    @PostMapping("/create")
    public ResponseEntity<CommentDto> createComment (@RequestBody Comment comment) {
        return new ResponseEntity<>(commentService.createComment(comment).get(), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<CommentDto>> getComment(@PathVariable("id") Long id) {
        return ResponseEntity.ok(commentService.getComment(id));
    }

    @PostMapping("/update")
    public ResponseEntity<Optional<CommentDto>> updateComment(@RequestBody Comment comment) {
        return ResponseEntity.ok(commentService.updateComment(comment));
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable("id") Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok("Comment with id " + id + " deleted.");
    }
}
