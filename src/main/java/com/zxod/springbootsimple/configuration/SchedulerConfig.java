package com.zxod.springbootsimple.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.ScheduledMethodRunnable;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
@ConditionalOnExpression("${scheduling.enable}") // 根据配置文件，决定要不要启用调度器
@EnableScheduling // 启动调度器
public class SchedulerConfig implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        if (scheduledTaskRegistrar.hasTasks()) {
            System.out.println("configure scheduled task...");

            scheduledTaskRegistrar.setScheduler(setTaskExecutors());

            // 如果启动时，还有任务没跑完，就抛出警告日志。非必要
            List<CronTask> cronTasks = scheduledTaskRegistrar.getCronTaskList();
            if (!cronTasks.isEmpty()) {
                for (CronTask cronTask: cronTasks) {
                    logCronTask(cronTask);
                }
            }
        }
    }

    // 设定执行线程数
    public ScheduledExecutorService setTaskExecutors() {
        return Executors.newScheduledThreadPool(10);
    }

    // 打印出未执行完的任务
    private void logCronTask(CronTask cronTask) {
        if (cronTask.getRunnable() instanceof ScheduledMethodRunnable) {
            System.out.printf("CronTask: %s - %s", ((ScheduledMethodRunnable) cronTask.getRunnable()).getMethod().getDeclaringClass().getSimpleName(), cronTask.getExpression());
        } else {
            System.out.printf("Other type of CronTask's runnable: %s - %s", cronTask.getRunnable().getClass().getSimpleName(), cronTask.getExpression());
        }
    }
}
