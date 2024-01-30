package com.pjg.secreto.user.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private String prodiver;
    private String email;
    private String userName;
    private String profileUrl;
}
