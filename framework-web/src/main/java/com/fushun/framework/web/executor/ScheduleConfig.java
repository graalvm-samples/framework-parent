package com.fushun.framework.web.executor;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executors;

@Configuration
public class ScheduleConfig implements SchedulingConfigurer {


    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        //当然了，这里设置的线程池是corePoolSize也是很关键了，自己根据业务需求设定
        TaskScheduler taskScheduler = new MyTaskScheduler(Executors.newScheduledThreadPool(1, Thread.ofVirtual().factory()));
        taskRegistrar.setScheduler(taskScheduler);
    }
}
