package org.example.social.user;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class User {
    private final Map<String, String> userList = new HashMap<>();

    public void addUser(String email, String nickName){
        userList.put(email, nickName);
    }
}
