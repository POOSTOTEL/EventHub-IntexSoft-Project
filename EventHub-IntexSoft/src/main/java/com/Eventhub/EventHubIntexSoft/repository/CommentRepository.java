package com.Eventhub.EventHubIntexSoft.repository;

import com.Eventhub.EventHubIntexSoft.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

  Comment findCommentByCommentId(Long commentId);

  void deleteCommentByCommentId(Long commentId);

  List<Comment> findAllByCommentId(Long commentId);
}
