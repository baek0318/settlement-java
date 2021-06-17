package com.pair.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {

    private String password = "password";

    @Test
    void 로그인성공() {
        User user = User.builder()
                .email("email@naver.com")
                .nickName("nickname")
                .password(password)
                .build();
        user.login(password);
    }

    @Test
    void 로그인실패() {
        User user = User.builder()
                .email("email@naver.com")
                .nickName("nickname")
                .password("wrong")
                .build();
        Assertions.assertThatThrownBy(() -> {
            user.login(password);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
