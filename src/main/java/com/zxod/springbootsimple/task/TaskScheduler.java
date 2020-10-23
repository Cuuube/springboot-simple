package com.zxod.springbootsimple.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskScheduler {
    @Scheduled(cron="${scheduling.task.testTask.cron:-}")
    public void testTask(){
        // trigger(TriggerTaskMessage.builder().taskName("testTask").build());
        System.out.println("testTask!");
    }
}
