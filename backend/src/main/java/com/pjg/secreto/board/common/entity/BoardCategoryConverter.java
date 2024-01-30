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
        return BoardCategory.valueOf(s);
    }
}
