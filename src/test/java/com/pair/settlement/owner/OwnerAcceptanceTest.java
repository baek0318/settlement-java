package com.pair.settlement.owner;

import com.pair.settlement.TestConfig;
import com.pair.settlement.dbutil.DatabaseCleanup;
import com.pair.settlement.dbutil.DatabaseInsert;
import com.pair.settlement.owner.dto.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestConfig.class)
public class OwnerAcceptanceTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DatabaseCleanup cleanup;

    @Autowired
    private DatabaseInsert dbInsert;

    @LocalServerPort
    private int port;

    @AfterEach
    void tearDown() {
        cleanup.execute();
    }

    @Test
    void 업주_등록_테스트() {
        OwnerEnrollRequest enrollRequest = new OwnerEnrollRequest("peach", "peach@kakao.com","01022223333");
        HttpEntity<OwnerEnrollRequest> request = new HttpEntity<>(enrollRequest);

        ResponseEntity<OwnerEnrollResponse> response= restTemplate.exchange(
                "/owner",
                HttpMethod.POST,
                request,
                OwnerEnrollResponse.class
        );

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(response.getBody().getId()).isEqualTo(1L);
    }

    @Test
    void 업주계좌_등록_테스트() {
        dbInsert.saveOwner(Owner.builder()
                .name("업주")
                .email("email@naver.com")
                .phoneNumber("010-3333-4444")
                .build());
        AccountEnrollRequest enrollRequest = new AccountEnrollRequest("은행", "214242-323-532", "계좌주");
        HttpEntity<AccountEnrollRequest> request = new HttpEntity<>(enrollRequest);

        ResponseEntity<AccountEnrollResponse> response= restTemplate.exchange(
                "/owner/{owner_id}/account",
                HttpMethod.POST,
                request,
                AccountEnrollResponse.class,
                1L
        );

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(response.getBody().getId()).isEqualTo(1L);
    }


    private Owner owner1;
    private Owner owner2;
    private Owner owner3;

    void 업주저장() {
        owner1 = Owner.builder()
                .name("peach")
                .email("peach@kakao.com")
                .phoneNumber("010-1111-2222")
                .build();
        owner2 = Owner.builder()
                .name("peach")
                .email("peach@gmail.com")
                .phoneNumber("010-1122-2222")
                .build();
        owner3 = Owner.builder()
                .name("apple")
                .email("apple@kakao.com")
                .phoneNumber("010-1111-2222")
                .build();
        dbInsert.saveOwner(owner1);
        dbInsert.saveOwner(owner2);
        dbInsert.saveOwner(owner3);

    }

    @Test
    void 아이디로_원하는_업주_가져오기() {
        업주저장();
        Map<String, String> queryParam = new HashMap<>();
        String id = String.valueOf(owner1.getId());
        queryParam.put("id", id);

        given()
                .port(port)
                .accept("application/json")
                .queryParams(queryParam)
        .when()
                .get("/owner")
        .then()
                .body("id", hasItems(Integer.parseInt(id)))
                .body("name", hasItems(owner1.getName()))
                .body("email", hasItems(owner1.getEmail()))
                .body("phoneNumber", hasItems(owner1.getPhoneNumber()));
    }

    @Test
    void 이름_쿼리파람으로_원하는_데이터_가져오기() {
        업주저장();
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("name", "peach");

        given()
                .port(port)
                .accept("application/json")
                .queryParams(queryParam)
        .when()
                .get("/owner")
        .then()
                .body("id", hasItems(owner1.getId().intValue(), owner2.getId().intValue()))
                .body("name", hasItems(owner1.getName(), owner2.getName()))
                .body("email", hasItems(owner1.getEmail(), owner2.getEmail()))
                .body("phoneNumber", hasItems(owner1.getPhoneNumber(), owner2.getPhoneNumber()));
    }

    @Test
    void 이메일_업주_검색_테스트() {
        업주저장();
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("email", "peach");

        given()
                .port(port)
                .accept("application/json")
                .queryParams(queryParam)
        .when()
                .get("/owner")
        .then()
                .body("id", hasItems(owner1.getId().intValue(), owner2.getId().intValue()))
                .body("name", hasItems(owner1.getName(), owner2.getName()))
                .body("email", hasItems(owner1.getEmail(), owner2.getEmail()))
                .body("phoneNumber", hasItems(owner1.getPhoneNumber(), owner2.getPhoneNumber()));
    }

    @Test
    void 이름과_이메일로_업주_검색_테스트() {
        업주저장();
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("email", "peach");
        queryParam.put("name", "apple");

        given()
                .port(port)
                .accept("application/json")
                .queryParams(queryParam)
        .when()
                .get("/owner")
        .then()
                .body("size", is(0));
    }

    @Test
    void 업주_업데이트하기() {
        업주저장();
        String updateName = "peach2";
        OwnerUpdateRequest request = OwnerUpdateRequest.builder()
                .id(owner1.getId())
                .email(owner1.getEmail())
                .name(updateName)
                .phoneNumber(owner1.getPhoneNumber())
                .build();

        given()
                .port(port)
                .accept("application/json")
                .contentType("application/json")
                .body(request)
        .when()
                .put("/owner")
        .then()
                .statusCode(200)
                .body("id", is(owner1.getId().intValue()))
                .body("name", equalTo(updateName));
    }

    @Test
    void 업주_계좌_업데이트_테스트() {
        업주저장();
        Long savedId = dbInsert.saveAccount(owner1.getId(), "대구은행", "214242-323-532", "계좌주");
        String updateBankAccount = "214242-323-333";
        AccountUpdateRequest request = AccountUpdateRequest.builder()
                .id(savedId)
                .bank("대구은행")
                .bankAccount(updateBankAccount)
                .accountHolder("계좌주")
                .build();

        given()
                .port(port)
                .accept("application/json")
                .contentType("application/json")
                .body(request)
        .when()
                .put("/owner/{owner-id}/account", owner1.getId())
        .then()
                .statusCode(200)
                .body("ownerName", equalTo(owner1.getName()))
                .body("bankAccount", equalTo(updateBankAccount));
    }
}
