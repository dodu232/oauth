package org.example.social.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.example.social.auth.oauth.KakaoUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@RequiredArgsConstructor
@Component
public class JwtFilter extends GenericFilterBean {

    private final KakaoUtil kakaoUtil;
    private static final String[] WHITELIST = {
        "/",
        "/auth/login",
        "/auth/login/**",
        "/img/*"
    };

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        if (isLoginCheck(requestURI)) {
            chain.doFilter(request, response);
            return;
        }
        String token = resolveToken((HttpServletRequest) request);

        try {
            kakaoUtil.validateToken(token);
        } catch (WebClientResponseException.Unauthorized ex) {
            throw new RuntimeException(ex.getStatusCode() + " 만료된 토큰 입니다.");
        }

        chain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private boolean isLoginCheck(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITELIST, requestURI);
    }
}
