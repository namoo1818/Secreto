package com.pjg.secreto.board.common.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class BoardCategoryConverter implements AttributeConverter<BoardCategory, String> {
    @Override
    public String convertToDatabaseColumn(BoardCategory boardCategory) {
        return boardCategory.name();
    }

    @Override
    public BoardCategory convertToEntityAttribute(String s) {
        try {
            int intValue = Integer.parseInt(s);

            switch (intValue) {
                case 0:
                    return BoardCategory.BOAST;
                case 1:
                    return BoardCategory.CERTIFICATE;
                case 2:
                    return BoardCategory.NOTICE;
                default:
                    throw new IllegalArgumentException(String.format("게시판 카테고리에 %s가 존재하지 않습니다.", s));
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자로 변환할 수 없는 값입니다.", e);
        }
    }
}