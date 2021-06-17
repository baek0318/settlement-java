package com.pair.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Long join(String email, String nickName, String password) {
        User user = new User(email, nickName, password);
        User saved = userRepository.save(user);

        return saved.getId();
    }

    @Transactional(readOnly = true)
    public Long login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new IllegalArgumentException("찾을 수 없는 아이디입니다."));
        user.login(password);
        return user.getId();
    }
}
