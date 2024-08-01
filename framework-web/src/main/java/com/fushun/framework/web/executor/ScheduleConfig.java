package com.fushun.framework.web.executor;

import cn.hutool.core.util.ObjectUtil;
import com.fushun.framework.util.util.UUIDUtil;
import com.fushun.framework.web.config.filter.LogCostFilter;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.ScheduledMethodRunnable;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

@Configuration
public class ScheduleConfig implements SchedulingConfigurer {


    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        //当然了，这里设置的线程池是corePoolSize也是很关键了，自己根据业务需求设定
        TaskScheduler taskScheduler = new MyTaskScheduler(Executors.newScheduledThreadPool(1, Thread.ofVirtual().factory()));
        taskRegistrar.setScheduler(taskScheduler);
    }
}
