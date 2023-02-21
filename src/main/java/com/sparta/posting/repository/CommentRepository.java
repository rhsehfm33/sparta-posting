package com.sparta.posting.repository;

import com.sparta.posting.entity.Comment;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@Qualifier("CommentRepositoryImpl")
public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {
    Optional<Comment> findByIdAndBoard_Id(Long commentId, Long boardId);

    void deleteAllByBoard_Id(Long boardId);
}
