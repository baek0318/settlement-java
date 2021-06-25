package com.pair.batch.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Scheduler {

    @Scheduled(fixedDelay = 1000)
    public void run() {
        log.info(">>>>>>>>run scheduler");
    }
}
