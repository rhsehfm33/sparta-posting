package com.sparta.posting.repository;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.posting.entity.QComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {
    private final JPAQueryFactory queryFactory;

//    @Override
//    public void deleteByBoard_Id(Long boardId) {
//        QComment comment = QComment.comment;
//
//        // Create a subquery to select the comment IDs to be deleted
//        JPAQuery<Long> subquery = queryFactory.select(comment.id).from(comment).where(comment.board.id.eq(boardId));
//
//        // Use the subquery to delete the comments
//        queryFactory.delete(comment).where(comment.id.in(subquery)).execute();
//    }
}
