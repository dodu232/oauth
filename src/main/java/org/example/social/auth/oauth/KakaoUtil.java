package org.example.social.auth.oauth;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Slf4j
@Component
public class KakaoUtil {

    @Value("${spring.kakao.auth.client}")
    private String client;
    @Value("${spring.kakao.auth.redirect}")
    private String redirect;
    @Value("${spring.kakao.auth.requestToken}")
    private String requestTokenURL;
    @Value("${spring.kakao.auth.requestProfile}")
    private String requestProfileURL;
    @Value("${spring.kakao.auth.validToken}")
    private String validTokenURL;
    HttpClient httpClient;

    public KakaoUtil() {
        this.httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .responseTimeout(Duration.ofMillis(5000))
            .doOnConnected(conn ->
                conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                    .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));
    }


    public KakaoDto.OAuthToken tokenRequest(String accessCode) {
        var webClient = WebClient.builder()
            .baseUrl(requestTokenURL)
            .defaultHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();

        return webClient.post()
            .body(BodyInserters.fromFormData("grant_type", "authorization_code")
                .with("client_id", client)
                .with("redirect_uri", redirect)
                .with("code", accessCode))
            .retrieve()
            .bodyToMono(KakaoDto.OAuthToken.class)
            .block();
    }

    public KakaoDto.KakaoProfile profileRequest(String accessToken) {
        var webClient = WebClient.builder()
            .baseUrl(requestProfileURL)
            .defaultHeader("Authorization", "Bearer " + accessToken)
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();

        return webClient.get()
            .retrieve()
            .bodyToMono(KakaoDto.KakaoProfile.class)
            .block();
    }

    public void validateToken(String accessToken) {
        var webClient = WebClient.builder()
            .baseUrl(requestProfileURL)
            .defaultHeader("Authorization", "Bearer " + accessToken)
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();

        webClient.get()
            .retrieve()
            .bodyToMono(KakaoDto.tokenValid.class)
            .block();
    }
}
