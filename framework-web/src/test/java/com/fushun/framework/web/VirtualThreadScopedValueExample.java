package com.fushun.framework.web;


import java.util.concurrent.Executors;

public class VirtualThreadScopedValueExample {
    private static final ScopedValue<String> REQUEST_ID = ScopedValue.newInstance();

    public static void main(String[] args) throws InterruptedException {
        var executor = Executors.newThreadPerTaskExecutor(Thread.ofVirtual().factory());

        ScopedValue.where(REQUEST_ID, "req-123").run(() -> {
            executor.execute(() -> {
                ScopedValue.where(REQUEST_ID, "req-1234").run(() -> {
                    // 在虚拟线程中访问 ScopedValue
                    System.out.println("Request ID in virtual thread: " + REQUEST_ID.get());
                });
            });
            executor.execute(() -> {
                ScopedValue.where(REQUEST_ID, "req-1235").run(() -> {
                    // 在虚拟线程中访问 ScopedValue
                    System.out.println("Request ID in virtual thread: " + REQUEST_ID.get());
                });
            });
            System.out.println("Request ID in virtual thread: " + REQUEST_ID.get());
        });

        // 等待线程执行完成
        Thread.sleep(500);
        executor.close();
    }
}

