package com.isa.spring.cache.memcached.config;

import com.google.code.ssm.spring.SSMCacheManager;
import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = GeneralCacheConfiguration.class)
public class GeneralCacheConfigurationTest {
    @Autowired
    private ApplicationContext context;

    private static final String CACHE_MANAGER = "cacheManager";

    @Autowired
    private CacheProperties cacheProperties;

    @Test
    public void shouldFetchSingleCacheManager() {
        final int expectedSize = 1;
        Map<String, CacheManager> managers = context.getBeansOfType(CacheManager.class);

        assertNotNull(managers);
        assertEquals(expectedSize, managers.size());
    }

    @Test
    public void shouldFetchMemcachedCacheManager() {
        CacheManager cacheManager = context.getBean(CACHE_MANAGER, CacheManager.class);

        assertNotNull(cacheManager);
        assertEquals(SSMCacheManager.class, cacheManager.getClass());
    }

    @Test
    public void shouldListCacheNames() {
        final String cacheName = "myCache";
        CacheManager cacheManager = context.getBean(CACHE_MANAGER, CacheManager.class);

        assertNotNull(cacheManager.getCache(cacheName));
    }
}
