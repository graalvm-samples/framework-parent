package com.fushun.framework.web.executor;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SpringStartedListener implements ApplicationListener<ContextStartedEvent>{
    @Override
    public void onApplicationEvent(ContextStartedEvent event) {
        log.info("============SpringStartedListener 执行=========== ");
    }
}
