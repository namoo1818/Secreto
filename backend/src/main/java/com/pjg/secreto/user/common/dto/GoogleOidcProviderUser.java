package com.pjg.secreto.user.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Map;

@Builder
@Data
@EqualsAndHashCode(callSuper=false)
public class GoogleOidcProviderUser extends AbstractOAuth2Provider {
    private Map<String, Object> attributes;
    private OAuth2User oAuth2User;
    private ClientRegistration clientRegistration;

    public GoogleOidcProviderUser (Map<String, Object> attributes,
                                  OAuth2User oAuth2User,
                                  ClientRegistration clientRegistration) {
        super(attributes, oAuth2User, clientRegistration);
        this.attributes = attributes;
        this.oAuth2User = oAuth2User;
        this.clientRegistration = clientRegistration;
    }

    @Override
    public String getId() {
        return (String) attributes.get("email");
    }

    @Override
    public String getUsername() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getProvider() {
        return clientRegistration.getRegistrationId();
    }

    @Override
    public String getProfileUrl() {
        return (String) attributes.get("profile");
    }

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getSub() {
        return (String) attributes.get("sub");
    }
}
