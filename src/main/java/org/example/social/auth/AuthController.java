package org.example.social.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    public String login() {
        return "index";
    }

    @GetMapping("/login/kakao")
    public String oauthCallback(
        @RequestParam("code") String accessCode,
        Model model
    ) {
        String accessToken = authService.getAccessToken(accessCode);
        String name = authService.getNickname(accessToken);
        model.addAttribute("message", "어서오세요 " + name + "님");
        model.addAttribute("token", "accessToken: " + accessToken);
        return "loginSuccess";
    }
}
