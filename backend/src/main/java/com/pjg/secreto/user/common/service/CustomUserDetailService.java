package com.pjg.secreto.user.common.service;

import com.pjg.secreto.user.command.service.UserCommandService;
import com.pjg.secreto.user.common.converters.ProviderUserConverter;
import com.pjg.secreto.user.common.converters.ProviderUserRequest;
import com.pjg.secreto.user.common.dto.PrincipalUser;
import com.pjg.secreto.user.common.dto.ProviderUser;
import com.pjg.secreto.user.common.entity.User;
import com.pjg.secreto.user.query.repository.UserQueryRepository;
import com.pjg.secreto.user.query.service.UserQueryService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService extends AbstractOAuth2UserService implements UserDetailsService {
    private final UserQueryRepository userQueryRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomUserDetailService(UserQueryRepository userQueryRepository,
                                   UserCommandService userCommandService,
                                   ProviderUserConverter<ProviderUserRequest, ProviderUser> providerUserConverter,
                                   PasswordEncoder passwordEncoder) {
        super(userQueryRepository, userCommandService, providerUserConverter);
        this.userQueryRepository = userQueryRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userQueryRepository.findByEmail(username);

        if(user.isEmpty()){
            throw new UsernameNotFoundException("해당 유저를 찾을 수 없습니다.");
        }

        ProviderUserRequest userRequest = new ProviderUserRequest(user.orElseThrow());
        ProviderUser providerUser = providerUser(userRequest);

        return new PrincipalUser(providerUser);
    }
}
