package com.isa.spring.cache.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.support.NoOpCacheManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class CustomCacheResolver implements CacheResolver {

    private final CacheableOperations operations;

    private final CacheProperties cacheProperties;

    private final CacheManager noOpCacheManager;

    public CustomCacheResolver(CacheProperties cacheProperties, CacheableOperations operations, CacheManager noOpCacheManager) {
        this.cacheProperties = cacheProperties;
        this.operations = operations;
        this.noOpCacheManager = noOpCacheManager;
    }

    @Override
    public Collection<? extends Cache> resolveCaches(CacheOperationInvocationContext<?> context) {
        if (!cacheProperties.isEnabled()) {
            return getCaches(noOpCacheManager, context);
        }

        Collection<Cache> caches = new ArrayList<>();
        CacheableOperation operation = operations.get(context);
        if (operation != null) {
            CacheManager cacheManager = operation.getCacheManager();
            if (cacheManager != null) {
                caches = getCaches(cacheManager, context);
            }
        }

        return caches;
    }

    private Collection<Cache> getCaches(CacheManager cacheManager, CacheOperationInvocationContext<?> context) {
        return context.getOperation().getCacheNames().stream()
                .map(cacheName -> cacheManager.getCache(cacheName))
                .filter(cache -> cache != null)
                .collect(Collectors.toList());
    }
}
