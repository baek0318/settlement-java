package com.pair.batch.job;

import com.pair.order.OrderTable;
import com.pair.settle.Settle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SettleJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final EntityManager em;


    private final int CHUNCKSIZE = 1;

    @Bean
    public Job settleJob() {
        return jobBuilderFactory.get("settleJob")
                .start(settleStep())
                .build();
    }

    @Bean
    public Step settleStep() {
        return stepBuilderFactory.get("settleStep")
                .<OrderTable, Settle>chunk(CHUNCKSIZE)
                .reader(orderReader())
                .processor(itemProcessor())
                .writer(orderWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<OrderTable> orderReader() {
        return new JpaPagingItemReaderBuilder<OrderTable>()
                .name("orderReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(CHUNCKSIZE)
                .queryString("SELECT o FROM com.pair.order.OrderTable o ORDER BY o.id")
                .build();
    }

    @Bean
    public ItemProcessor<OrderTable, Settle> itemProcessor() {
        return order -> Settle.builder()
                .amount(order.getTotalPrice())
                .dateTime(LocalDateTime.now())
                .owner(order.getOwner())
                .build();
    }

    @Bean
    public ItemWriter<Settle> orderWriter() {
        return new JpaItemWriterBuilder<Settle>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }
}
