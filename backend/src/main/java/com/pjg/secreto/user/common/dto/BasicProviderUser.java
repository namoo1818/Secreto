package com.pjg.secreto.user.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Map;

@Builder
@AllArgsConstructor
@Data
public class BasicProviderUser implements ProviderUser{
    private String id;
    private String username;
    private String password;
    private String email;
    private String profileUrl;
    private String provider = "None";
    private List<? extends GrantedAuthority> authorities;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public String getProvider() {
        return provider;
    }

    @Override
    public String getProfileUrl() {
        return this.profileUrl;
    }

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public OAuth2User getOAuth2User() {
        return null;
    }

    @Override
    public String getSub() {
        return "";
    }
}
