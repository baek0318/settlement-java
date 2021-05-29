package com.pair.settlement.owner;

import com.pair.settlement.dbutil.DatabaseCleanup;
import com.pair.settlement.dbutil.DatabaseInsert;
import com.pair.settlement.owner.dto.AccountEnrollRequest;
import com.pair.settlement.owner.dto.AccountEnrollResponse;
import com.pair.settlement.owner.dto.OwnerEnrollRequest;
import com.pair.settlement.owner.dto.OwnerEnrollResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OwnerAcceptanceTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DatabaseCleanup cleanup;

    @Autowired
    private DatabaseInsert dbInsert;

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
}
