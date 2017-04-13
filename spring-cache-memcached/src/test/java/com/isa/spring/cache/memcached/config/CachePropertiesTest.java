package com.isa.spring.cache.memcached.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = GeneralCacheConfiguration.class)
public class CachePropertiesTest {

    @Autowired
    private CacheProperties cacheProperties;

    @Test
    public void shouldReturnEnabled() {
        assertTrue(cacheProperties.isEnabled());
    }

    @Test
    public void shouldReturnCacheName() {
        final String expectedCacheName = "myCache";
        final String actualCacheName = cacheProperties.getName();

        assertNotNull(actualCacheName);
        assertEquals(expectedCacheName, actualCacheName);
    }

    @Test
    public void shouldReturnTimeout() {
        final int expectedTimeout = 150;
        final int actualTimeout = cacheProperties.getTimeout();

        assertEquals(expectedTimeout, actualTimeout);
    }

    @Test
    public void shouldReturnDefaultTtl() {
        final int expectedDefaultTtl = 100;
        final int actualDefaultTtl = cacheProperties.getDefaultTtl();

        assertEquals(expectedDefaultTtl, actualDefaultTtl);
    }

    @Test
    public void shouldReturnServers() {
        final String expectedServers = "localhost:11211";
        final String actualServers = cacheProperties.getServers();

        assertEquals(expectedServers, actualServers);
    }
}
