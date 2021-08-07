package com.project.garden.core;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocal {
    private static final java.lang.ThreadLocal<Map<String, Object>> context = new java.lang.ThreadLocal<>();

    public static java.lang.ThreadLocal<Map<String, Object>> getContext() {
        return context;
    }

    public static Object setObject(String key, Object object) {
        if (context.get() == null)
            context.set(new HashMap<>());
        return context.get().put(key, object);
    }

    public static Object getObject(String key) {
        return context.get().get(key);
    }

    public static void clear() {
        context.get().clear();
    }
}

