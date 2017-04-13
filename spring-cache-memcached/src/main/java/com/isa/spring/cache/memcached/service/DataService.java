package com.isa.spring.cache.memcached.service;

public interface DataService {
    String get(String key);

    String update(String key, String value);

    void delete(String key);

    int getAccessCount();
}
