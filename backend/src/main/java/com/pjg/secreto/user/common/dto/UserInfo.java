package com.pjg.secreto.user.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private String prodiver;
    private String email;
    private String userName;
    private String profileUrl;
    private String id;

    public UserInfo(ProviderUser providerUser) {
        this.prodiver = providerUser.getProvider();
        this.email = providerUser.getEmail();
        this.userName = providerUser.getUsername();
        this.profileUrl = providerUser.getProfileUrl();
        this.id = providerUser.getId();
    }
}
