package com.isa.spring.cache.config;


import org.apache.commons.lang3.ArrayUtils;
import org.springframework.cache.CacheManager;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

public class CacheableOperation<T> {
    private final Class<T> targetClass;

    private final String[] methodNames;

    private final CacheManager cacheManager;

    private CacheableOperation(Class<T> targetClass, String[] methodNames, CacheManager cacheManager) {
        this.targetClass = targetClass;
        this.methodNames = methodNames;
        this.cacheManager = cacheManager;
    }

    public Class<T> getTargetClass() {
        return targetClass;
    }

    public String[] getMethodNames() {
        return methodNames;
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public static class Builder<T> {
        private final Class<T> targetClass;

        private String[] methodNames;

        private CacheManager cacheManager;

        private Map<String, Method> methods = new HashMap<>();

        public Builder(Class<T> targetClass) {
            this.targetClass = targetClass;
            Arrays.stream(targetClass.getDeclaredMethods())
                    .forEachOrdered(method -> methods.put(method.getName(), method));
        }

        public Builder<T> method(String... methodNames) {
            this.methodNames = methodNames;
            return this;
        }

        public Builder<T> cacheManager(CacheManager cacheManager) {
            this.cacheManager = cacheManager;
            return this;
        }

        public CacheableOperation<T> build() {
            checkArgument(targetClass != null);
            checkArgument(ArrayUtils.isNotEmpty(methodNames));
            checkArgument(Arrays.stream(methodNames).allMatch(name -> methods.get(name) != null));

            return new CacheableOperation<T>(targetClass, methodNames, cacheManager);
        }
    }


}
