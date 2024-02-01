package com.pjg.secreto.board.common.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.stream.Stream;

public enum BoardCategory {
    BOAST("자랑","0"), CERTIFICATE("인증","1"), NOTICE("공지","2");

    private String desc;
    private String legacyCode;

    BoardCategory(String desc, String legacyCode){
        this.desc = desc;
        this.legacyCode = legacyCode;
    }

    @JsonCreator
    public static BoardCategory from(@JsonProperty("boardCategory")String s){
        if (s == null) {
            throw new IllegalArgumentException("게시판 카테고리는 null이 될 수 없습니다.");
        }
        return Stream.of(BoardCategory.values())
                .filter(v->v.name().equals(s.toUpperCase()))
                .findAny()
                .orElseThrow(()->new IllegalArgumentException(String.format("게시판 카테고리에 %s가 존재하지 않습니다.", s)));    }

}
