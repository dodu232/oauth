package org.example.social.user;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final User user;

    public Map<String, String> getUserList(){
        return user.getUserList();
    }
}
