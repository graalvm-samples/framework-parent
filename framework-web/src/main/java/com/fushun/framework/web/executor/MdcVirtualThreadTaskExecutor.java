package com.fushun.framework.web.executor;

import org.slf4j.MDC;
import org.springframework.lang.NonNull;

import java.util.Map;
import java.util.concurrent.*;

public class MdcVirtualThreadTaskExecutor implements Executor{

    private final ExecutorService executor;
    final private boolean useFixedContext;
    final private Map<String, String> fixedContext;

    /**
     * Factory method to create an executor that uses virtual threads with inherited MDC.
     */
    public static MdcVirtualThreadTaskExecutor newWithInheritedMdc() {
        return new MdcVirtualThreadTaskExecutor(null);
    }

    private MdcVirtualThreadTaskExecutor(Map<String, String> fixedContext) {
        this.executor = Executors.newVirtualThreadPerTaskExecutor();
        this.fixedContext = fixedContext;
        this.useFixedContext = (fixedContext != null);
    }

    private Map<String, String> getContextForTask() {
        return useFixedContext ? fixedContext : MDC.getCopyOfContextMap();
    }

    /**
     * Executes the given task with MDC context.
     */
    public void execute(@NonNull Runnable command) {
        executor.execute(wrap(command, getContextForTask()));
    }

    @NonNull
    public Future<?> submit(@NonNull Runnable task) {
        return executor.submit(wrap(task, getContextForTask()));
    }

    @NonNull
    public <T> Future<T> submit(@NonNull Callable<T> task) {
        return executor.submit(wrap(task, getContextForTask()));
    }

    private static <T> Callable<T> wrap(final Callable<T> task, final Map<String, String> context) {
        return () -> {
            Map<String, String> previous = MDC.getCopyOfContextMap();
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            try {
                return task.call();
            } finally {
                if (previous == null) {
                    MDC.clear();
                } else {
                    MDC.setContextMap(previous);
                }
            }
        };
    }

    private static Runnable wrap(final Runnable runnable, final Map<String, String> context) {
        return () -> {
            Map<String, String> previous = MDC.getCopyOfContextMap();
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            try {
                runnable.run();
            } finally {
                if (previous == null) {
                    MDC.clear();
                } else {
                    MDC.setContextMap(previous);
                }
            }
        };
    }

    /**
     * Shutdown the executor and release resources.
     */
    public void shutdown() {
        executor.shutdown();
    }
}