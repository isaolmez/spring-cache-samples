package com.isa.spring.cache.vanilla.customannotation;

import com.isa.spring.cache.config.CacheConfiguration;
import com.isa.spring.cache.vanilla.DataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CacheConfiguration.class)
public class DataServiceTest {
    @Autowired
    @Qualifier("withCustomAnnotation")
    private DataService dataService;

    @Test
    public void shouldCache() {
        final int expectedAccessCount = 0;
        final String key = "key";
        final String value = "value";
        dataService.update(key, value);
        dataService.get(key);
        dataService.get(key);
        dataService.get(key);

        int actualAccessCount = dataService.getAccessCount();

        assertEquals(expectedAccessCount, actualAccessCount);
    }

    @Test
    public void shouldPutToCache() {
        final String key = "key";
        final String value = "newValue";
        final String expectedValue = value;

        dataService.update(key, value);
        String actualValue = dataService.get(key);

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void shouldEvictCache() {
        final String key = "key";
        final String value = "value";
        final String expectedValue = null;

        dataService.update(key, value);
        dataService.delete(key);
        String actualValue = dataService.get(key);

        assertEquals(expectedValue, actualValue);
    }


}
