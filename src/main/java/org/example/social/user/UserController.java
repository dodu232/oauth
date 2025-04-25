package org.example.social.user;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public String getUserList(
        Model model
    ) {
        Map<String, String> map = userService.getUserList();
        model.addAttribute("entries", map.entrySet());
        return "userList";
    }
}
