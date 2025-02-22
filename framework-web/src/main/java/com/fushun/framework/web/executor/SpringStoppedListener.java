package com.fushun.framework.web.executor;




import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SpringStoppedListener implements ApplicationListener<ContextStoppedEvent>{

    @Override
    public void onApplicationEvent(ContextStoppedEvent event) {
        log.info("============SpringStoppedListener 执行=========== ");
    }

}
