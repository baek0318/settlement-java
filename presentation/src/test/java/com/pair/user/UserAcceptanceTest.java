package com.pair.user;

import com.pair.TestConfig;
import com.pair.dbutil.DatabaseCleanup;
import com.pair.dbutil.DatabaseInsert;
import com.pair.user.dto.request.JoinRequest;
import com.pair.user.dto.response.JoinResponse;
import com.pair.user.dto.request.LoginRequest;
import com.pair.user.dto.response.LoginResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestConfig.class)
public class UserAcceptanceTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DatabaseCleanup dbClean;

    @Autowired
    private DatabaseInsert dbInsert;

    @AfterEach
    void tearDown() {
        dbClean.execute();
    }

    private String email = "peach@kakao.com";
    private String nickName = "peach";
    private String password = "1234";

    @Test
    void 유저_가입_테스트(){

        HttpEntity<JoinRequest> joinRequest = new HttpEntity<>(
                new JoinRequest(email, nickName, password));

        ResponseEntity<JoinResponse> response = restTemplate.exchange(
                "/user",
                HttpMethod.POST,
                joinRequest,
                JoinResponse.class
        );

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(response.getBody().getId()).isEqualTo(1L);
    }

    @Test
    void 유저_로그인_테스트() {
        dbInsert.saveUser(User.builder()
                .email(email)
                .nickName(nickName)
                .password(password)
                .build());

        HttpEntity<LoginRequest> loginRequest = new HttpEntity<>(
                new LoginRequest(email,  password));

        ResponseEntity<LoginResponse> response = restTemplate.exchange(
                "/user/login",
                HttpMethod.POST,
                loginRequest,
                LoginResponse.class
        );

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getId()).isEqualTo(1L);
    }


}
