package com.pair.batch.job;

import com.pair.batch.config.TestBatchConfig;
import com.pair.batch.db.DatabaseInsert;
import com.pair.order.OrderDetail;
import com.pair.order.OrderStatus;
import com.pair.order.OrderTable;
import com.pair.order.PaymentMethod;
import com.pair.owner.Account;
import com.pair.owner.Owner;
import com.pair.user.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@SpringBatchTest
@SpringBootTest
@Import(value = DatabaseInsert.class)
@ContextConfiguration(classes = {SettleJobConfiguration.class, TestBatchConfig.class})
public class BatchTest {

    @Autowired
    JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    DatabaseInsert insert;

    @Test
    void batchTest() throws Exception {
        Owner owner = insert.saveOwner("peach", "peach@kakao.com", "010-2222-3333");
        OrderTable order1 = insert.saveOrder(owner, 7000, OrderStatus.WAITING, LocalDateTime.parse("2021-06-23T17:17:00"));
        OrderTable order2 = insert.saveOrder(owner, 6000, OrderStatus.WAITING, LocalDateTime.parse("2021-06-23T17:17:00"));
        OrderTable order3 = insert.saveOrder(owner, 5000, OrderStatus.WAITING, LocalDateTime.parse("2021-06-23T17:17:00"));
        OrderTable order4 = insert.saveOrder(owner, 4000, OrderStatus.WAITING, LocalDateTime.parse("2021-06-23T17:17:00"));
        OrderTable order5 = insert.saveOrder(owner, 3000, OrderStatus.WAITING, LocalDateTime.parse("2021-06-23T17:17:00"));

        Map<String, JobParameter> map = new HashMap<>();
        map.put("requestDate", new JobParameter("2021-06-23"));
        JobParameters jobParameters = new JobParameters(map);

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        Assertions.assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
    }


}
