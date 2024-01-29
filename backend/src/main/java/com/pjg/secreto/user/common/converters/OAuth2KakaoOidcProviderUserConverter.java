package com.pjg.secreto.user.common.converters;

import com.pjg.secreto.user.common.dto.KakaoOidcProviderUser;
import com.pjg.secreto.user.common.dto.ProviderUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

public final class OAuth2KakaoOidcProviderUserConverter implements ProviderUserConverter<ProviderUserRequest,ProviderUser> {
    @Override
    public ProviderUser convert(ProviderUserRequest providerUserRequest) {

        if (!providerUserRequest.clientRegistration().getRegistrationId().equals("kakao")) {
            return null;
        }

        if (!(providerUserRequest.oAuth2User() instanceof OidcUser)) {
            return null;
        }

        return new KakaoOidcProviderUser(
                providerUserRequest.oAuth2User().getAttributes(),
                providerUserRequest.oAuth2User(),
                providerUserRequest.clientRegistration());
    }
}
