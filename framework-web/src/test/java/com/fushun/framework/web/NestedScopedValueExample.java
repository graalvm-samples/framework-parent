package com.fushun.framework.web;

//import jdk.incubator.concurrent.ScopedValue;
import java.lang.ScopedValue;

public class NestedScopedValueExample {
    private static final ScopedValue<String> USER_ID = ScopedValue.newInstance();

    public static void main(String[] args) {
        ScopedValue.where(USER_ID, "outer-user").run(() -> {
            System.out.println("Outer User ID: " + USER_ID.get());

            ScopedValue.where(USER_ID, "inner-user").run(() -> {
                System.out.println("Inner User ID: " + USER_ID.get());
            });

            System.out.println("Back to Outer User ID: " + USER_ID.get());
        });
    }
}

