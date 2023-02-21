package com.sparta.posting.repository;

import com.sparta.posting.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndBoard_Id(Long commentId, Long boardId);

    void deleteAllByBoard_Id(Long boardId);
}
