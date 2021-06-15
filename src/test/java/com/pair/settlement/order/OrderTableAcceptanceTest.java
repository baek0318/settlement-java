package com.pair.settlement.order;

import com.pair.settlement.TestConfig;
import com.pair.settlement.dbutil.DatabaseCleanup;
import com.pair.settlement.dbutil.DatabaseInsert;
import com.pair.settlement.order.dto.*;
import com.pair.settlement.owner.Owner;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    private Owner owner;
    private Owner owner2;
    @BeforeEach
    void setUp() {
        owner = dbInsert.saveOwner("peach", "peach@kakao.com", "010-2222-2222");
        owner2 = dbInsert.saveOwner("peach", "berry@kakao.com", "010-3333-4444");
    }

    @AfterEach
    void tearDown() {
        dbCleanup.execute();
    }

    @Test
    void 주문_저장_테스트() {
        int price1 = 4000;
        int price2 = 3000;
        OrderDetailSaveRequest detailSaveRequest1 = OrderDetailSaveRequest.builder()
                .paymentMethod("CARD")
                .price(price1)
                .build();
        OrderDetailSaveRequest detailSaveRequest2 = OrderDetailSaveRequest.builder()
                .paymentMethod("COUPON")
                .price(price2)
                .build();
        OrderSaveRequest saveRequest = OrderSaveRequest.builder()
                .totalPrice(price1+price2)
                .createdAt(LocalDateTime.now())
                .details(Arrays.asList(detailSaveRequest1, detailSaveRequest2))
                .ownerId(owner.getId())
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

    @Test
    void 업주_아이디로_주문검색() {
        OrderTable orderTable = dbInsert.saveOrder(owner, 7000, OrderStatus.WAITING, LocalDateTime.now());
        OrderTable orderTable2 = dbInsert.saveOrder(owner, 5000, OrderStatus.WAITING, LocalDateTime.now());

        OrderInfoListResponse response =
                given()
                        .port(port)
                        .accept("application/json")
                        .contentType("application/json")
                        .queryParam("owner-id", owner.getId())
                .when()
                        .get("/order")
                .thenReturn()
                        .body()
                        .as(OrderInfoListResponse.class);

        response.getOrderInfoList().sort(Comparator.comparingLong(OrderInfoResponse::getOrderId));
        List<OrderTable> expected = Arrays.asList(orderTable, orderTable2);
        List<OrderInfoResponse> actual = response.getOrderInfoList();
        orderInfoAssert(expected, actual);
    }

    @Test
    void 주문_아이디로_검색() {
        OrderTable orderTable = dbInsert.saveOrder(owner, 7000, OrderStatus.WAITING, LocalDateTime.now());
        OrderTable orderTable2 = dbInsert.saveOrder(owner, 5000, OrderStatus.WAITING, LocalDateTime.now());

        OrderInfoListResponse response =
                given()
                        .port(port)
                        .accept("application/json")
                        .contentType("application/json")
                        .queryParam("order-id", orderTable.getId())
                .when()
                        .get("/order")
                .thenReturn()
                        .body()
                        .as(OrderInfoListResponse.class);

        response.getOrderInfoList().sort(Comparator.comparingLong(OrderInfoResponse::getOrderId));
        List<OrderTable> expected = Collections.singletonList(orderTable);
        List<OrderInfoResponse> actual = response.getOrderInfoList();
        orderInfoAssert(expected, actual);
    }

    @Test
    void 업주아이디와_날짜_범위로_검색() {
        String dateTime1 = "2021-06-15T04:17";
        String dateTime2 = "2021-06-15T04:20";
        OrderTable orderTable = dbInsert.saveOrder(owner, 7000, OrderStatus.WAITING, LocalDateTime.parse(dateTime1));
        OrderTable orderTable2 = dbInsert.saveOrder(owner, 5000, OrderStatus.WAITING, LocalDateTime.parse(dateTime2));
        OrderTable orderTable3 = dbInsert.saveOrder(owner2, 4000, OrderStatus.WAITING, LocalDateTime.parse(dateTime2));

        OrderInfoListResponse response =
                given()
                        .port(port)
                        .accept("application/json")
                        .contentType("application/json")
                        .queryParam("owner-id", owner.getId())
                        .queryParam("fromDateTime", "2021-06-15T04:17")
                        .queryParam("toDateTime", "2021-06-15T04:20")
                .when()
                        .get("/order")
                .thenReturn()
                        .body()
                        .as(OrderInfoListResponse.class);

        response.getOrderInfoList().sort(Comparator.comparingLong(OrderInfoResponse::getOrderId));
        List<OrderTable> expected = Arrays.asList(orderTable, orderTable2);
        List<OrderInfoResponse> actual = response.getOrderInfoList();
        orderInfoAssert(expected, actual);
    }

    private void orderInfoAssert(List<OrderTable> expected, List<OrderInfoResponse> actual) {
        Assertions.assertThat(expected.size()).isEqualTo(actual.size());
        for(int i = 0; i < expected.size(); i++) {
            Assertions.assertThat(actual.get(i).getOrderId()).isEqualTo(expected.get(i).getId());
            Assertions.assertThat(actual.get(i).getStatus()).isEqualTo(expected.get(i).getStatus());
            Assertions.assertThat(actual.get(i).getTotalPrice()).isEqualTo(expected.get(i).getTotalPrice());
            Assertions.assertThat(actual.get(i).getCreatedAt()).isEqualTo(expected.get(i).getCreatedAt());
        }
    }

    @Test
    void 주문아이디로_상세_주문_검색() {
        String dateTime1 = "2021-06-15T04:17:00";
        OrderTable orderTable = dbInsert.saveOrder(owner, 7000, OrderStatus.WAITING, LocalDateTime.parse(dateTime1));
        OrderDetail detail1 = dbInsert.saveOrderDetail(orderTable, PaymentMethod.CARD, 4000);
        OrderDetail detail2 = dbInsert.saveOrderDetail(orderTable, PaymentMethod.COUPON, 3000);

        OrderInfoResponse response =
                given()
                        .port(port)
                        .accept("application/json")
                .when()
                        .get("/order/{order-id}/detail", orderTable.getId())
                .thenReturn()
                        .body()
                        .as(OrderInfoResponse.class);

        List<OrderDetail> expected = Arrays.asList(detail1, detail2);
        List<OrderDetailInfoResponse> actual = response.getDetails();
        Assertions.assertThat(actual.size()).isEqualTo(expected.size());
        for(int i = 0; i < actual.size(); i++) {
            Assertions.assertThat(actual.get(i).getPrice()).isEqualTo(expected.get(i).getPrice());
            Assertions.assertThat(actual.get(i).getPaymentMethod()).isEqualTo(expected.get(i).getPaymentMethod());
            Assertions.assertThat(actual.get(i).getOrderDetailId()).isEqualTo(expected.get(i).getId());
        }
    }
}
