package com.isa.spring.cache.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CacheConfiguration.class)
@TestPropertySource(properties = {"cache.enabled = true", "cache.names = test1,test2"})
public class CachePropertiesTest {

    @Autowired
    private CacheProperties cacheProperties;

    @Test
    public void shouldReturnEnabled() {
        assertTrue(cacheProperties.isEnabled());
    }

    @Test
    public void shouldReturnCacheNames() {
        final int expectedSize = 2;
        final String firstExpectedCache = "test1";
        final String secondExpectedCache = "test2";
        final List<String> cacheNames = cacheProperties.getCacheNameList();

        assertNotNull(cacheNames);
        assertEquals(expectedSize, cacheNames.size());
        assertTrue(cacheNames.contains(firstExpectedCache));
        assertTrue(cacheNames.contains(secondExpectedCache));
    }
}
