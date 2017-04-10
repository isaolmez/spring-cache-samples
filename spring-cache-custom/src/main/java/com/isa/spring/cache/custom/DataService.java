package com.isa.spring.cache.custom;

public interface DataService {
    String getX(String key);

    String getY(String key);

    String getZ(String key);

    int getAccessCountForX();

    int getAccessCountForY();

    int getAccessCountForZ();
}
