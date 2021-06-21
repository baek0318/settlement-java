package com.pair.batch.job;

import com.pair.batch.config.TestBatchConfig;
import com.pair.batch.job.SettleJobConfiguration;
import com.pair.order.OrderRepository;
import com.pair.order.OrderTable;
import com.pair.owner.OwnerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@SpringBatchTest
@ContextConfiguration(classes = {SettleJobConfiguration.class, TestBatchConfig.class})
public class BatchTest {

    @Autowired
    JobLauncherTestUtils jobLauncherTestUtils;


    @Test
    void batchTest() throws Exception {
        Map<String, JobParameter> map = new HashMap<>();
        map.put("requestDate", new JobParameter(LocalDate.now().toString()));
        JobParameters jobParameters = new JobParameters(map);

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        Assertions.assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
    }
}
