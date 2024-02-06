package com.pjg.secreto.board.command.service;

import com.pjg.secreto.board.command.dto.UpdateBoardRequestDto;
import com.pjg.secreto.board.command.dto.UpdateReplyRequestDto;
import com.pjg.secreto.board.command.dto.WriteBoardRequestDto;
import com.pjg.secreto.board.command.dto.WriteReplyRequestDto;

public interface BoardCommandService {
    public Long updatePost(Long boardNo, Long roomNo, Long userNo, UpdateBoardRequestDto updateBoardRequestDto);

    public void deletePost(Long roomNo, Long userNo, Long boardNo);

    public void writePost(Long roomNo, Long userNo, WriteBoardRequestDto writeBoardRequestDto);

    public void updateLike(Long roomNo, Long userNo, Long boardNo);

    public void deleteLike(Long roomNo, Long userNo, Long roomUserNo);

    public void writeReply(Long boardNo, Long roomNo, Long userNo, WriteReplyRequestDto writeReplyRequestDto);

    public void deleteReply(Long boardNo, Long roomNo, Long replyNo);

    public Long updateReply(Long boardNo, Long roomNo, Long replyNo, UpdateReplyRequestDto updateReplyRequestDto);
}
