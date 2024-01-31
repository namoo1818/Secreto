package com.pjg.secreto.user.common.service;

import com.pjg.secreto.user.command.service.UserCommandService;
import com.pjg.secreto.user.common.converters.ProviderUserConverter;
import com.pjg.secreto.user.common.converters.ProviderUserRequest;
import com.pjg.secreto.user.common.dto.ProviderUser;
import com.pjg.secreto.user.common.entity.User;
import com.pjg.secreto.user.query.repository.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public abstract class AbstractOAuth2UserService {
    private final UserQueryRepository userQueryRepository;
    private final UserCommandService userCommandService;

    private final ProviderUserConverter<ProviderUserRequest, ProviderUser> providerUserConverter;

    public User register(ProviderUser providerUser, OAuth2UserRequest userRequest){
        Optional<User> user = userQueryRepository.findByEmail(providerUser.getEmail());

        if(user.isEmpty()){
            return userCommandService.register(providerUser);
        }
        return user.orElseThrow();
    }

    public ProviderUser providerUser(ProviderUserRequest providerUserRequest){
        return providerUserConverter.convert(providerUserRequest);
    }
}
