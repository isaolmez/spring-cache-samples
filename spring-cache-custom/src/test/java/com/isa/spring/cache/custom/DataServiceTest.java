package com.isa.spring.cache.custom;

import com.isa.spring.cache.config.CacheConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CacheConfiguration.class)
public class DataServiceTest {

    @Autowired
    private DataService dataService;

    @Test
    public void shouldCacheX(){
        final int expectedAccessCount = 1;
        final String expectedValue = "X";

        final String key = "dummy";
        dataService.getX(key);
        dataService.getX(key);
        String actualValue = dataService.getX(key);
        assertEquals(expectedValue, actualValue);

        int actualAccessCount = dataService.getAccessCountForX();
        assertEquals(expectedAccessCount, actualAccessCount);
    }

    @Test
    public void shouldCacheY(){
        final int expectedAccessCount = 1;
        final String expectedValue = "Y";

        final String key = "dummy";
        dataService.getY(key);
        dataService.getY(key);
        String actualValue = dataService.getY(key);
        assertEquals(expectedValue, actualValue);

        int actualAccessCount = dataService.getAccessCountForY();
        assertEquals(expectedAccessCount, actualAccessCount);
    }

    @Test
    public void shouldCacheZ(){
        final int expectedAccessCount = 1;
        final String expectedValue = "Z";

        final String key = "dummy";
        dataService.getZ(key);
        dataService.getZ(key);
        String actualValue = dataService.getZ(key);
        assertEquals(expectedValue, actualValue);

        int actualAccessCount = dataService.getAccessCountForZ();
        assertEquals(expectedAccessCount, actualAccessCount);
    }
}
