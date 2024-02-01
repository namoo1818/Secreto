package com.pjg.secreto.user.common.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailValidationResponseDto {
    @Value("${email.expiration}")
    private long time;
}
