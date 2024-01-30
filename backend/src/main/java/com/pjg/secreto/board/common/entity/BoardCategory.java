package com.pjg.secreto.board.common.entity;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum BoardCategory {
    BOAST("자랑"), CERTIFICATE("인증"), NOTICE("공지");

    private String value;

    BoardCategory(String value){
        this.value = value;
    }

    public static BoardCategory from(String s){
        return Arrays.stream(BoardCategory.values())
                .filter(v->v.getValue().equals(s))
                .findAny()
                .orElseThrow(()->new IllegalArgumentException(String.format("게시판 카테고리에 %s가 존재하지 않습니다.", s)));    }

}
