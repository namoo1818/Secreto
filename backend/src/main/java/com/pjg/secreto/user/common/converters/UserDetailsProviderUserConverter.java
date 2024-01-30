package com.pjg.secreto.user.common.converters;


import com.pjg.secreto.user.common.dto.BasicProviderUser;
import com.pjg.secreto.user.common.dto.ProviderUser;
import com.pjg.secreto.user.common.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class UserDetailsProviderUserConverter implements ProviderUserConverter<ProviderUserRequest,ProviderUser> {

    @Override
    public ProviderUser convert(ProviderUserRequest providerUserRequest) {

        if(providerUserRequest.user() == null){
            return null;
        }

        User user = providerUserRequest.user();
        return BasicProviderUser.builder()
                .id(user.getId().toString())
                .username(user.getNickname())
                .password(user.getPassword())
                .authorities(null)
                .email(user.getEmail())
                .provider("none")
                .build();
    }
}
