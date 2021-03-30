package com.ismayfly.coins.tools.core.cache;

import com.google.common.collect.Lists;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Component;

@Component
public class MayflyCacheCustomizer implements CacheManagerCustomizer<RedisCacheManager> {
    @Override
    public void customize(RedisCacheManager cacheManager) {
//        cacheManager.setCacheNames( Lists.newArrayList("orderQueue","bill"));
    }

}
