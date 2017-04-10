package com.isa.spring.cache.config;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CacheConfiguration.class)
public class CacheConfigurationTest {
    @Autowired
    private ApplicationContext context;

    private static final String CACHE_MANAGER = "cacheManager";
    private static final String EHCACHE_CACHE_MANAGER = "ehCacheCacheManager";
    private static final String NOOP_CACHE_MANAGER = "noOpCacheManager";

    @Autowired
    private CacheProperties cacheProperties;

    @Test
    public void shouldFetchSimpleCacheManager() {
        CacheManager cacheManager = context.getBean(CACHE_MANAGER, CacheManager.class);

        assertNotNull(cacheManager);
        assertEquals(SimpleCacheManager.class, cacheManager.getClass());
    }

    @Test
    public void shouldFetchEhCacheCacheManager() {
        CacheManager cacheManager = context.getBean(EHCACHE_CACHE_MANAGER, CacheManager.class);

        assertNotNull(cacheManager);
        assertEquals(EhCacheCacheManager.class, cacheManager.getClass());
    }

    @Test
    public void shouldFetchNoOpCacheManager() {
        CacheManager cacheManager = context.getBean(NOOP_CACHE_MANAGER, CacheManager.class);

        assertNotNull(cacheManager);
        assertEquals(NoOpCacheManager.class, cacheManager.getClass());
    }

    @Test
    public void shouldCreateSingletonInstances() {
        final int expectedSize = 3;
        Map<String, CacheManager> cacheManagers = context.getBeansOfType(CacheManager.class);

        assertNotNull(cacheManagers);
        assertEquals(expectedSize, cacheManagers.size());
    }

    @Test
    public void shouldListCacheNames_ForSimpleCacheManager() {
        CacheManager cacheManager = context.getBean(CACHE_MANAGER, CacheManager.class);
        List<String> cacheNames = ImmutableList.of("cacheForX", "cacheForY", "cacheForZ");

        for (String cacheName : cacheNames) {
            assertNotNull(cacheManager.getCache(cacheName));
        }
    }

    @Test
    public void shouldListCacheNames_ForEhCacheCacheManager() {
        CacheManager cacheManager = context.getBean(EHCACHE_CACHE_MANAGER, CacheManager.class);
        List<String> cacheNames = ImmutableList.of("cacheForZ");

        for (String cacheName : cacheNames) {
            assertNotNull(cacheManager.getCache(cacheName));
        }
    }
}
