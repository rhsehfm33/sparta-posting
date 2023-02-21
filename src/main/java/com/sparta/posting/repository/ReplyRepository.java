package com.sparta.posting.repository;

import com.sparta.posting.entity.Board;
import com.sparta.posting.entity.Comment;
import com.sparta.posting.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByComment_Id(Long boardId);
}
