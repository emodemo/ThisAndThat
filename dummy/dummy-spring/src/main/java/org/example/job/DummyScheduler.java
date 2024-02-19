package org.example.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Service
@Slf4j
public class DummyScheduler {

    // @Scheduled(cron = "${cron.expression}") // set in application.properties, non-standard as it allows for seconds
    // "0/10 * * * * *" -> every 10 seconds
    // @Scheduled(cron = Scheduled.CRON_DISABLED) // or with the example above and cron.expression = "-"
    // @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS) // wait between consecutive runs
    // @Scheduled(fixedRate = 10, timeUnit = TimeUnit.SECONDS)
    public void my1stJob() {
        log.info("this is joooob!");
    }

}
