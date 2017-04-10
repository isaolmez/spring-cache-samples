package com.isa.spring.cache.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class CacheProperties {
    @Value("${cache.enabled}")
    private boolean enabled;

    @Value("${cache.names}")
    private String[] cacheNames;

    public List<String> getCacheNameList() {
        return Collections.unmodifiableList(Arrays.asList(cacheNames));
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String[] getCacheNames() {
        return cacheNames;
    }

    public void setCacheNames(String[] cacheNames) {
        this.cacheNames = cacheNames;
    }
}
