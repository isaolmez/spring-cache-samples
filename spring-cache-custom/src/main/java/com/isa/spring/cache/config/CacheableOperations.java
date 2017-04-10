package com.isa.spring.cache.config;

import com.google.common.collect.ImmutableMap;
import com.isa.spring.cache.custom.DataServiceImpl;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheableOperations {
    private final CacheManager cacheManager;

    private final CacheManager ehCacheCacheManager;

    private final CacheManager noOpCacheManager;

    public CacheableOperations(CacheManager cacheManager, CacheManager ehCacheCacheManager, CacheManager noOpCacheManager) {
        this.cacheManager = cacheManager;
        this.ehCacheCacheManager = ehCacheCacheManager;
        this.noOpCacheManager = noOpCacheManager;
    }

    private Map<String, CacheableOperation<?>> opMap;

    public void init() {
        List<CacheableOperation<? extends Class>> ops = new ArrayList<>();
        ops.add(new CacheableOperation.Builder(DataServiceImpl.class)
                .method("getX", "getY")
                .cacheManager(cacheManager)
                .build());
        ops.add(new CacheableOperation.Builder(DataServiceImpl.class)
                .method("getZ")
                .cacheManager(ehCacheCacheManager)
                .build());
        postProcessOperations(ops);
    }

    public CacheableOperation<?> get(CacheOperationInvocationContext<?> context) {
        final String queryKey = getOperationKey(context.getTarget().getClass().getName(),
                context.getMethod().getName());
        return opMap.get(queryKey);
    }

    private void postProcessOperations(List<CacheableOperation<? extends Class>> ops) {
        Map<String, CacheableOperation<?>> tempMap = new HashMap<>();
        for (CacheableOperation<?> op : ops) {
            for (String methodName : op.getMethodNames()) {
                tempMap.put(getOperationKey(op.getTargetClass().getName(), methodName), op);
            }
        }

        opMap = ImmutableMap.copyOf(tempMap);
    }

    private String getOperationKey(String first, String second) {
        return String.format("%s-%s", first, second);
    }
}
