package org.example.social.auth;

import lombok.RequiredArgsConstructor;
import org.example.social.auth.oauth.KakaoDto;
import org.example.social.auth.oauth.KakaoUtil;
import org.example.social.user.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final KakaoUtil kakaoUtil;
    private final User user;

    public String getNickname(String accessToken) {
        KakaoDto.KakaoProfile kakaoProfile = kakaoUtil.profileRequest(accessToken);

        String email = kakaoProfile.getKakaoAccount().getEmail();
        String nickName = kakaoProfile.getKakaoAccount().getProfile().getNickname();

        if (!user.getUserList().containsKey(email)) {
            user.addUser(email, nickName);
        }

        return nickName;
    }

    public String getAccessToken(String accessCode) {
        KakaoDto.OAuthToken oAuthToken = kakaoUtil.tokenRequest(accessCode);
        return oAuthToken.getAccess_token();
    }
}
