package com.isa.spring.cache.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@ComponentScan("com.isa.spring.cache")
@PropertySource("application.properties")
@EnableCaching
public class CacheConfiguration extends CachingConfigurerSupport {

    private final CacheProperties cacheProperties;

    @Autowired
    public CacheConfiguration(CacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
    }

    @Bean
    @Primary
    @Override
    public CacheManager cacheManager() {
        if (cacheProperties.isEnabled()) {
            return simpleCacheManager();
        } else {
            return new NoOpCacheManager();
        }
    }

    private CacheManager simpleCacheManager(){
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        List<ConcurrentMapCache> caches = Arrays.stream(cacheProperties.getCacheNames()).map(cacheName -> new ConcurrentMapCache(cacheName)).collect(Collectors.toList());
        cacheManager.setCaches(caches);
        return cacheManager;
    }
}
