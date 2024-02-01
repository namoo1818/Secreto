package com.pjg.secreto.common.Util;

import com.pjg.secreto.user.common.dto.PrincipalUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

public class AuthUtils {
    public static Long getAuthenticatedUserId(Authentication authentication){

        Assert.notNull(authentication, "인증되지 않았습니다.");

        PrincipalUser principal = (PrincipalUser) authentication.getPrincipal();
        String id = principal.providerUser().getId();

        Long userNo = Long.parseLong(id);

        return userNo;
    }

    public static Long getAuthenticatedUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return getAuthenticatedUserId(authentication);
    }
}
