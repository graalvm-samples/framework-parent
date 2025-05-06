package com.fushun.framework.dynamic.datasource;

import lombok.extern.slf4j.Slf4j;

import java.util.Stack;

@Slf4j
public class DataSourceContextHolder {
    private static final ThreadLocal<Stack<String>> contextHolder = new ThreadLocal<>();

    public static void setDataSource(String dataSourceName) {
        Stack<String> stack = contextHolder.get();
        if (stack == null) {
            stack = new Stack<>();
            contextHolder.set(stack);
        }
        stack.push(dataSourceName);
    }

    public static String getDataSource() {
        Stack<String> stack = contextHolder.get();
        if (stack != null && !stack.isEmpty()) {
            return stack.peek();
        }
        return null;
    }

    public static void clearDataSource() {
        Stack<String> stack = contextHolder.get();
        if (stack != null && !stack.isEmpty()) {
            stack.pop();
            if (stack.isEmpty()) {
                contextHolder.remove();
                log.info("clearDataSource");
            }
        }
    }
}

