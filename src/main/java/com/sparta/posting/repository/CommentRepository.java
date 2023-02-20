package com.sparta.posting.repository;

import com.sparta.posting.entity.Board;
import com.sparta.posting.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndBoard_Id(Long commentId, Long boardId);
}
