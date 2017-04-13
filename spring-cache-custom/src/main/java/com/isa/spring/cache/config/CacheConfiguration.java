package com.isa.spring.cache.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@ComponentScan("com.isa.spring.cache")
@PropertySource("application.properties")
@EnableCaching
public class CacheConfiguration extends CachingConfigurerSupport {

    @Autowired
    private final CacheProperties cacheProperties;

    public CacheConfiguration(CacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
    }

    @Bean
    @Override
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        List<ConcurrentMapCache> caches = Arrays.stream(cacheProperties.getCacheNames()).map(cacheName -> new ConcurrentMapCache(cacheName)).collect(Collectors.toList());
        cacheManager.setCaches(caches);
        return cacheManager;
    }

    @Bean
    @Override
    public CacheResolver cacheResolver() {
        return new CustomCacheResolver(cacheProperties, operations(), noOpCacheManager());
    }

    @Bean
    public CacheManager ehCacheCacheManager() {
        return new EhCacheCacheManager(ehCacheCacheManagerFactory().getObject());
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheCacheManagerFactory() {
        EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
        cmfb.setConfigLocation(new ClassPathResource("ehcache.xml"));
        cmfb.setShared(true);
        return cmfb;
    }

    @Bean
    public CacheManager noOpCacheManager() {
        return new NoOpCacheManager();
    }

    @Bean
    public CacheableOperations operations() {
        CacheableOperations operations = new CacheableOperations(cacheManager(), ehCacheCacheManager(), noOpCacheManager());
        operations.init();
        return operations;
    }
}
