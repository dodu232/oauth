package org.example.social.auth.oauth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

public class KakaoDto {

    @Getter
    public static class OAuthToken{
        private String access_token;
        private String token_type;
        private String refresh_token;
        private int expires_in;
        private String scope;
        private int refresh_token_expires_in;
    }

    @Getter
    public static class KakaoProfile{
        private Long id;
        @JsonProperty("connected_at")
        private String connectedAt;
        private Properties properties;
        @JsonProperty("kakao_account")
        private KakaoAccount kakaoAccount;

        @Getter
        public static class Properties{
            private String nickname;
        }

        @Getter
        public static class KakaoAccount {
            private Boolean profile_nickname_needs_agreement;
            private Profile profile;
            private Boolean has_email;
            private Boolean email_needs_agreement;
            private Boolean is_email_valid;
            private Boolean is_email_verified;
            private String email;

            @Getter
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Profile {
                private String nickname;
                private Boolean is_default_nickname;
            }
        }

    }

    @Getter
    public static class tokenValid{
        private Long id;
        private int expires_in;
        private int app_id;
    }

}
