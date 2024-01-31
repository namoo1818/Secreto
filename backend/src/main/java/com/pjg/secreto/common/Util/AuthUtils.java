package com.pjg.secreto.common.Util;

import com.pjg.secreto.user.common.dto.PrincipalUser;
import com.pjg.secreto.user.common.dto.ProviderUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

public class AuthUtils {
    public static String getAuthenticatedUserId(Authentication authentication){

        Assert.notNull(authentication, "인증되지 않았습니다.");

        PrincipalUser principal = (PrincipalUser) authentication.getPrincipal();
        String id = principal.providerUser().getId();

        return id;
    }

    public static String getAuthenticatedUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return getAuthenticatedUserId(authentication);
    }

    public static String getAuthenticatedUserEmail(Authentication authentication) {
        PrincipalUser principal = (PrincipalUser) authentication.getPrincipal();
        ProviderUser providerUser = principal.providerUser();
        String userEmail = providerUser.getEmail();
        return userEmail;
    }

    public static String getAuthenticatedUserEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return getAuthenticatedUserEmail(authentication);
    }



}
