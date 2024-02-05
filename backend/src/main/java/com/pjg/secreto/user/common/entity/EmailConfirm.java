package com.pjg.secreto.user.common.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "emailConfirm" )
@Data
public class EmailConfirm {
    @Indexed
    private String email;

    @Id
    private String validationCode;

    private boolean isChecked;

    public EmailConfirm(String email, String validationCode, boolean isChecked) {
        this.email = email;
        this.validationCode = validationCode;
        this.isChecked = isChecked;
    }
}
