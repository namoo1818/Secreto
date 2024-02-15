package com.pjg.secreto.user.common.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Map;

@Data
@Builder
@EqualsAndHashCode(callSuper=false)
public class KakaoOidcProviderUser extends AbstractOAuth2Provider {
    private Map<String, Object> attributes;
    private OAuth2User oAuth2User;
    private ClientRegistration clientRegistration;

    public KakaoOidcProviderUser(Map<String, Object> attributes,
                                 OAuth2User oAuth2User,
                                 ClientRegistration clientRegistration) {
        super(attributes, oAuth2User, clientRegistration);
        this.attributes = attributes;
        this.oAuth2User = oAuth2User;
        this.clientRegistration = clientRegistration;
    }

    @Override
    public String getId() {
        return super.getId();
    }

    @Override
    public String getUsername() {
        return (String)getAttributes().get("nickname");
    }

    @Override
    public String getProfileUrl() {
        return (String)getAttributes().get("profile_image_url");
    }

    @Override
    public String getEmail() {
        return (String) getAttributes().get("email");
    }

    @Override
    public String getProvider() {
        return clientRegistration.getRegistrationId();
    }

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public OAuth2User getOAuth2User() {
        return oAuth2User;
    }

    @Override
    public String getSub() {
        return (String) attributes.get("sub");
    }
}
