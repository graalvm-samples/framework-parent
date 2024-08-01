package com.fushun.framework.web.executor;

import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;

import java.util.concurrent.Executors;


/**
 * 替换spring 异步任务执行
 * @Async
 */
@Configuration(proxyBeanMethods = false)
public class ExecutorConfig {

    /**
     * 使用自定义的异步执行器执行异步
     * @return
     */
    @Primary
    @Bean
    public AsyncTaskExecutor taskExecutor() {
        return new TaskExecutorAdapter(MdcVirtualThreadTaskExecutor.newWithInheritedMdc());
    }

    /**
     * 自定义的调用执行延迟执行任务
     * @return
     */
    @Bean
    public MyTaskScheduler myTaskScheduler(){
        return new MyTaskScheduler(Executors.newScheduledThreadPool(1, Thread.ofVirtual().factory()));
    }

    /**
     * 定制Tomcat服务器的协议处理器。具体来讲，它配置了Tomcat的线程执行器，使用了Java的虚拟线程（Virtual Threads）
     * @return
     */
    @Bean
    public TomcatProtocolHandlerCustomizer<?> protocolHandlerCustomizer() {
        return protocolHandler -> {
            protocolHandler.setExecutor(MdcVirtualThreadTaskExecutor.newWithInheritedMdc());
        };
    }
}
