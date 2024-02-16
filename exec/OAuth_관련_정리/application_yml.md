1. application.yml
```yaml
spring:
   profiles:
   default: dev, security, aws
```

2.application-security.yml 생성 후 아래와 같이 설정
```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: 구글 클라이언트 ID
            client-secret: 구글 클라이언트 시크릿키
            redirect-uri: 서버주소/login/oauth2/code/google
            scope: openid,profile,email

          kakao:
            client-id: 카카오 Rest API 키 
            authorization-grant-type: authorization_code
            redirect-uri: 서버주소/login/oauth2/code/kakao
            scope: openid,profile_nickname,profile_image,account_email

        provider:
          kakao:
            issuer-uri: https://kauth.kakao.com
            authorization-uri: https://kauth.kakao.com/oauth/authorize
            token-uri: https://kauth.kakao.com/oauth/token
            user-info-uri: https://kapi.kakao.com/v2/user/me
            user-name-attribute: sub
```
※ 참고

- 구글 OAuth 클라이언트 Id, 구글 클라이언트 시크릿키
![2024-02-15 12 04 42 (13).png](google_auth_img%2F2024-02-15%2012%2004%2042%20%2813%29.png)

- 카카오 Rest API 키 
![RestAPIKey.png](kakao_auth_img%2FRestAPIKey.png)


3. OAuthSuccessHandler 코드 수정
- com.pjg.secreto.user.common.handler.OAuthSuccessHandler
```java
@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final String redirectUrl = "프론트엔드 주소/oauth/redirect";
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        
    }
}


```
