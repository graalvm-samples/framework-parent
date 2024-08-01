package com.fushun.framework.web.executor;


import cn.hutool.core.util.ObjectUtil;
import com.fushun.framework.base.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SpringClosedListener  implements ApplicationListener<ContextClosedEvent> {

    @Autowired
    private AsyncTaskExecutor asyncTaskExecutor;

    @Autowired
    private MyTaskScheduler myTaskScheduler;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        log.info("============SpringClosedListener 执行=========== ");
        // 关闭线程池
        if (asyncTaskExecutor instanceof ThreadPoolTaskExecutor) {
            ((ThreadPoolTaskExecutor) asyncTaskExecutor).destroy();
        }

    }
}