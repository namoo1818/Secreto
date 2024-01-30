package com.pjg.secreto.history.query.dto;


import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@Data
@Builder
@JsonTypeName("post")
@EqualsAndHashCode
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class PostDto {
    private Long postId;
    private String title;
    private String content;
    private String registeredAt;
    private String nickName;
    private long hit;
}
