package com.pjg.secreto.user.query.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DetailRequestDto {
    @NotBlank(message = "유저의 정보가 빈 값으로 전달 되었습니다")
    private String userId;
}
