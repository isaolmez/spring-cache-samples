package com.isa.spring.cache.vanilla.cacheable;

import com.isa.spring.cache.vanilla.DataService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service("vanilla")
public class DataServiceImpl implements DataService {

    private Map<String, String> backingDataStore = new HashMap<>();
    private AtomicInteger accessCount = new AtomicInteger(0);

    @Cacheable(cacheNames = "myCache", key = "#key")
    @Override
    public String get(String key) {
        accessCount.incrementAndGet();
        return backingDataStore.get(key);
    }

    @CachePut(cacheNames = "myCache", key = "#key")
    @Override
    public String update(String key, String value) {
        this.backingDataStore.put(key, value);
        return value;
    }

    @CacheEvict(cacheNames = "myCache", key = "#key")
    @Override
    public void delete(String key) {
        this.backingDataStore.remove(key);
    }

    @Override
    public int getAccessCount() {
        return accessCount.get();
    }
}
