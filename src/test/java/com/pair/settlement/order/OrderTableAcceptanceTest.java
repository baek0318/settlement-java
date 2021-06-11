package com.pair.settlement.order;

import com.pair.settlement.TestConfig;
import com.pair.settlement.dbutil.DatabaseCleanup;
import com.pair.settlement.dbutil.DatabaseInsert;
import com.pair.settlement.order.dto.OrderDetailSaveRequest;
import com.pair.settlement.order.dto.OrderSaveRequest;
import com.pair.settlement.owner.Owner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestConfig.class)
public class OrderTableAcceptanceTest {

    @Autowired
    private DatabaseInsert dbInsert;

    @Autowired
    private DatabaseCleanup dbCleanup;

    @LocalServerPort
    private int port;

    @AfterEach
    void tearDown() {
        dbCleanup.execute();
    }

    @Test
    void 주문_저장_테스트() {
        Owner owner = Owner.builder()
                .name("peach")
                .email("peach@kakao.com")
                .phoneNumber("010-2222-2222")
                .build();
        Long ownerId = dbInsert.saveOwner(owner);
        int price1 = 4000;
        int price2 = 3000;
        OrderDetailSaveRequest detailSaveRequest1 = OrderDetailSaveRequest.builder()
                .paymentMethod("card")
                .price(price1)
                .build();
        OrderDetailSaveRequest detailSaveRequest2 = OrderDetailSaveRequest.builder()
                .paymentMethod("coupon")
                .price(price2)
                .build();
        OrderSaveRequest saveRequest = OrderSaveRequest.builder()
                .totalPrice(price1+price2)
                .createdAt(LocalDateTime.now())
                .details(Arrays.asList(detailSaveRequest1, detailSaveRequest2))
                .ownerId(ownerId)
                .status("WAITING")
                .build();

        given()
                .port(port)
                .accept("application/json")
                .contentType("application/json")
                .body(saveRequest)
        .when()
                .post("/order")
        .then()
                .statusCode(201)
                .body("id", is(1))
                .body("detailIds", hasItems(1, 2));
    }


}
