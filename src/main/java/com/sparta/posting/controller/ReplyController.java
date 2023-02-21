package com.sparta.posting.controller;

import com.sparta.posting.dto.ApiResponse;
import com.sparta.posting.dto.ReplyOuterResponseDto;
import com.sparta.posting.dto.ReplyRequestDto;
import com.sparta.posting.security.UserDetailsImpl;
import com.sparta.posting.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/replies")
public class ReplyController {
    private final ReplyService replyService;

    @PostMapping
    public ApiResponse<ReplyOuterResponseDto> createReply(
            @RequestBody @Valid ReplyRequestDto replyRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
    ) {
        return replyService.createReply(replyRequestDto, userDetailsImpl);
    }

    @GetMapping("/{commentId}")
    public ApiResponse<List<ReplyOuterResponseDto>> getReplies(
            @PathVariable Long commentId
    ) {
        return replyService.getReplies(commentId);
    }

    @PutMapping("/{replyId}")
    public ApiResponse<ReplyOuterResponseDto> updateReply(
            @RequestBody @Valid ReplyRequestDto replyRequestDto,
            @PathVariable Long replyId,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
    ) {
        return replyService.updateReply(replyRequestDto, replyId, userDetailsImpl);
    }

    @DeleteMapping("/{replyId}")
    public ApiResponse<ReplyOuterResponseDto> deleteReply(
            @RequestBody @Valid ReplyRequestDto replyRequestDto,
            @PathVariable Long replyId,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
    ) {
        return replyService.deleteReply(replyRequestDto, replyId, userDetailsImpl);
    }
}
