package com.thaipd.sbprac.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/*
    configuration for scheduler
 */
@Configuration
public class SchedulerConfig implements SchedulingConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(SchedulerConfig.class);
    @Value("${scheduler.thread.pool.size}")
    private int POOL_SIZE;

    @Value("${scheduler.thread.pool.nameprefix}")
    private String POOL_NAMEPREFIX;
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();

        scheduler.setPoolSize(POOL_SIZE);
        scheduler.setThreadNamePrefix(POOL_NAMEPREFIX);
        scheduler.initialize();

        taskRegistrar.setTaskScheduler(scheduler);
    }
}