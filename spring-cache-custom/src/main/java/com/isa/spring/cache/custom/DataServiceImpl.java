package com.isa.spring.cache.custom;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class DataServiceImpl implements DataService {
    private AtomicInteger accessCountForX = new AtomicInteger(0);
    private AtomicInteger accessCountForY = new AtomicInteger(0);
    private AtomicInteger accessCountForZ = new AtomicInteger(0);

    @Cacheable(cacheNames = "cacheForX", key = "#key")
    @Override
    public String getX(String key) {
        accessCountForX.incrementAndGet();
        return "X";
    }

    @Cacheable(cacheNames = "cacheForY", key = "#key")
    @Override
    public String getY(String key) {
        accessCountForY.incrementAndGet();
        return "Y";
    }

    @Cacheable(cacheNames = "cacheForZ", key = "#key")
    @Override
    public String getZ(String key) {
        accessCountForZ.incrementAndGet();
        return "Z";
    }

    @Override
    public int getAccessCountForX() {
        return accessCountForX.get();
    }

    @Override
    public int getAccessCountForY() {
        return accessCountForY.get();
    }

    @Override
    public int getAccessCountForZ() {
        return accessCountForZ.get();
    }
}
