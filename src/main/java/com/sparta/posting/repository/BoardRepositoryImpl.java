package com.sparta.posting.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.posting.dto.BoardWholeResponseDto;
import com.sparta.posting.dto.CommentOuterResponseDto;
import com.sparta.posting.dto.UserOuterResponseDto;
import com.sparta.posting.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.querydsl.core.group.GroupBy.groupBy;


@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Modifying
    @Override
    public List<BoardWholeResponseDto> findAllByOrderByCreatedAtDesc() {
        QBoard board = QBoard.board;
        QUser user = QUser.user;
        QComment comment = QComment.comment;

        List<BoardWholeResponseDto> boardDtoList = queryFactory
                .selectFrom(board)
                .leftJoin(board.user, user)
                .leftJoin(comment)
                .on(comment.board.eq(board))
                .orderBy(board.createdAt.desc(), comment.createdAt.desc())
                .transform(groupBy(board.id).list(
                        Projections.constructor(
                                BoardWholeResponseDto.class,
                                board.id,
                                board.category,
                                board.boardContent,
                                board.createdAt,
                                board.modifiedAt,
                                board.boardLikeSet.size().longValue(),
                                Projections.constructor(
                                        UserOuterResponseDto.class,
                                        user.id,
                                        user.username,
                                        user.email,
                                        user.createdAt,
                                        user.modifiedAt
                                ),
                                Projections.list(
                                        Projections.constructor(
                                                CommentOuterResponseDto.class,
                                                comment.id,
                                                comment.commentContent,
                                                comment.createdAt,
                                                comment.modifiedAt,
                                                comment.likedCommentSet.size().longValue()
                                        )
                                )
                        )
                ));
        return boardDtoList;

    }
}
