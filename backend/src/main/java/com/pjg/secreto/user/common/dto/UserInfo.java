package com.pjg.secreto.user.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private String provider;
    private String email;
    private String nickname;
    private String profileUrl;
    private String id;

    public UserInfo(ProviderUser providerUser) {
        this.provider = providerUser.getProvider();
        this.email = providerUser.getEmail();
        this.nickname = providerUser.getUsername();
        this.profileUrl = providerUser.getProfileUrl();
        this.id = providerUser.getId();
    }
}
