package com.isa.spring.cache.config;

import com.google.common.collect.ImmutableList;
import com.isa.spring.cache.config.CacheConfiguration;
import com.isa.spring.cache.config.CacheProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CacheConfiguration.class)
@TestPropertySource(properties = {"cache.enabled = true", "cache.names = test1,test2"})
public class CacheConfigurationTest {
    @Autowired
    private ApplicationContext context;

    private static final String CACHE_MANAGER = "cacheManager";

    @Autowired
    private CacheProperties cacheProperties;

    @Test
    public void shouldFetchSimpleCacheManager() {
        CacheManager cacheManager = context.getBean(CACHE_MANAGER, CacheManager.class);

        assertNotNull(cacheManager);
        assertEquals(SimpleCacheManager.class, cacheManager.getClass());
    }

    @Test
    public void shouldListCacheNames() {
        CacheManager cacheManager = context.getBean(CACHE_MANAGER, CacheManager.class);
        List<String> cacheNames = ImmutableList.of("test1", "test2");

        for (String cacheName : cacheNames) {
            assertNotNull(cacheManager.getCache(cacheName));
        }
    }
}
