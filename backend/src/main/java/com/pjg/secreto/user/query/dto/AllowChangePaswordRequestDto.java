package com.pjg.secreto.user.query.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AllowChangePaswordRequestDto {
    @NotBlank(message = "검증코드가 빈 값으로 전달되었습니다.")
    private String certCode;
}
