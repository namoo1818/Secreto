package com.pjg.secreto.user.common.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Map;

public interface ProviderUser {
    String getId();
    String getUsername();
    String getPassword();
    String getEmail();
    String getProvider();
    String getProfileUrl();
    List<? extends GrantedAuthority> getAuthorities();
    Map<String, Object> getAttributes();
    OAuth2User getOAuth2User();

    Long getSub();
}
