package com.pair.settlement.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long userJoin(String email, String nickName, String password) {

        User user = new User(email, nickName, password);
        User saved = userRepository.save(user);

        return saved.getId();
    }

    public Long login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new IllegalArgumentException("찾을 수 없는 아이디입니다."));
        user.login(password);
        return user.getId();
    }
}
