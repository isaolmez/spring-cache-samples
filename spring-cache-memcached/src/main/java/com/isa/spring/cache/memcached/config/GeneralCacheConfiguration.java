package com.isa.spring.cache.memcached.config;

import com.google.code.ssm.CacheFactory;
import com.google.code.ssm.config.AddressProvider;
import com.google.code.ssm.config.DefaultAddressProvider;
import com.google.code.ssm.providers.CacheClientFactory;
import com.google.code.ssm.providers.CacheConfiguration;
import com.google.code.ssm.providers.xmemcached.MemcacheClientFactoryImpl;
import com.google.code.ssm.spring.SSMCache;
import com.google.code.ssm.spring.SSMCacheManager;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan("com.isa.spring.cache.memcached")
@PropertySource("application.properties")
@EnableCaching
public class GeneralCacheConfiguration extends CachingConfigurerSupport {

    private final CacheProperties cacheProperties;

    @Autowired
    public GeneralCacheConfiguration(CacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
    }

    @Bean
    @Primary
    @Override
    public CacheManager cacheManager() {
        if (cacheProperties.isEnabled()) {
            try {
                return memcachedCacheManager();
            } catch (Exception e) {
                throw new IllegalStateException("CacheManager for memcached cannot be created", e);
            }
        } else {
            return new NoOpCacheManager();
        }
    }

    private CacheManager memcachedCacheManager() throws Exception {
        SSMCacheManager cacheManager = new SSMCacheManager();
        SSMCache cache = new SSMCache(cacheFactory().getObject(), cacheProperties.getDefaultTtl(), false);
        cacheManager.setCaches(ImmutableList.of(cache));
        return cacheManager;
    }

    @Bean
    public CacheFactory cacheFactory() {
        CacheFactory cacheFactory = new CacheFactory();
        cacheFactory.setCacheName(cacheProperties.getName());
        cacheFactory.setAddressProvider(addressProvider());
        cacheFactory.setConfiguration(configuration());
        cacheFactory.setCacheClientFactory(cacheClientFactory());
        return cacheFactory;
    }

    @Bean
    public CacheClientFactory cacheClientFactory() {
        return new MemcacheClientFactoryImpl();
    }

    @Bean
    public AddressProvider addressProvider() {
        DefaultAddressProvider addressProvider = new DefaultAddressProvider();
        addressProvider.setAddress(cacheProperties.getServers());
        return addressProvider;
    }

    @Bean
    public CacheConfiguration configuration() {
        CacheConfiguration configuration = new CacheConfiguration();
        configuration.setOperationTimeout(cacheProperties.getTimeout());
        configuration.setConsistentHashing(true);
        return configuration;
    }
}
