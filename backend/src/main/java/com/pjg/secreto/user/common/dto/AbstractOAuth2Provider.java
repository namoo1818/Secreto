package com.pjg.secreto.user.common.dto;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;
import java.util.UUID;

public abstract class AbstractOAuth2Provider implements ProviderUser {
    private Map<String, Object> attributes;
    private OAuth2User oAuth2User;
    protected ClientRegistration clientRegistration;

    public AbstractOAuth2Provider(Map<String, Object> attributes, OAuth2User oAuth2User, ClientRegistration clientRegistration) {
        this.attributes = attributes;
        this.oAuth2User = oAuth2User;
        this.clientRegistration = clientRegistration;
    }

    @Override
    public String getPassword() {
        return UUID.randomUUID().toString().substring(0, 15);
    }


}
