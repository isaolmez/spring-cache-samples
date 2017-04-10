package com.isa.spring.cache.vanilla;

public interface DataService {
    String get(String key);

    String update(String key, String value);

    void delete(String key);

    int getCount();
}
