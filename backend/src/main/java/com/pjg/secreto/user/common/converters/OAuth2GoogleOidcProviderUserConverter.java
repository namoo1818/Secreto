package com.pjg.secreto.user.common.converters;


import com.pjg.secreto.user.common.dto.GoogleOidcProviderUser;
import com.pjg.secreto.user.common.dto.ProviderUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


public final class OAuth2GoogleOidcProviderUserConverter implements ProviderUserConverter<ProviderUserRequest,ProviderUser> {
    @Override
    public ProviderUser convert(ProviderUserRequest providerUserRequest) {

        if (!providerUserRequest.clientRegistration().getRegistrationId().equals("google")) {
            return null;
        }

        return new GoogleOidcProviderUser(
                providerUserRequest.oAuth2User().getAttributes(),
                providerUserRequest.oAuth2User(),
                providerUserRequest.clientRegistration());
    }
}
